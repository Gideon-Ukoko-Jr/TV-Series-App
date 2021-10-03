package com.giko.tvapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.giko.tvapp.R;
import com.giko.tvapp.adapters.SeriesAdapter;
import com.giko.tvapp.databinding.ActivityMainBinding;
import com.giko.tvapp.model.Series;
import com.giko.tvapp.viewmodels.MostPopularSeriesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MostPopularSeriesViewModel viewModel;
    private ActivityMainBinding activityMainBinding;
    private List<Series> series = new ArrayList<>();
    private SeriesAdapter seriesAdapter;

    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        doInit();

    }

    private void doInit(){
        activityMainBinding.rvTVShows.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularSeriesViewModel.class);
        seriesAdapter = new SeriesAdapter(series);
        activityMainBinding.rvTVShows.setAdapter(seriesAdapter);

        activityMainBinding.rvTVShows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.rvTVShows.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePages){
                        currentPage += 1;
                        getMostPopularSeries();
                    }
                }
            }
        });
        getMostPopularSeries();
    }

    private void getMostPopularSeries(){
        toggleLoading();
        viewModel.getMostPopularSeries(currentPage).observe(this, mostPopularSeriesResponse -> {
            toggleLoading();
            if (mostPopularSeriesResponse != null){
                totalAvailablePages = mostPopularSeriesResponse.getTotalPages();

                if (mostPopularSeriesResponse.getTvSeries() != null){
                    int oldCount = series.size();
                    series.addAll(mostPopularSeriesResponse.getTvSeries());
                    seriesAdapter.notifyItemRangeInserted(oldCount, series.size());
                }
            }
        });
    }

    private void toggleLoading(){
        if (currentPage == 1){
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()){
                activityMainBinding.setIsLoading(false);
            }else {
                activityMainBinding.setIsLoading(true);
            }
        }else {
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()){
                activityMainBinding.setIsLoadingMore(false);
            }else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }
}