package com.giko.tvapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

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
        getMostPopularSeries();
    }

    private void getMostPopularSeries(){
        activityMainBinding.setIsLoading(true);
        viewModel.getMostPopularSeries(0).observe(this, mostPopularSeriesResponse -> {
            activityMainBinding.setIsLoading(false);
            if (mostPopularSeriesResponse != null){
                if (mostPopularSeriesResponse.getTvSeries() != null){
                    series.addAll(mostPopularSeriesResponse.getTvSeries());
                    seriesAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}