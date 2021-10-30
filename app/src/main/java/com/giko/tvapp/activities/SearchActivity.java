package com.giko.tvapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.giko.tvapp.R;
import com.giko.tvapp.adapters.SeriesAdapter;
import com.giko.tvapp.databinding.ActivitySearchBinding;
import com.giko.tvapp.listeners.SeriesListener;
import com.giko.tvapp.model.Series;
import com.giko.tvapp.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements SeriesListener {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel searchViewModel;
    private List<Series> series = new ArrayList<>();
    private SeriesAdapter seriesAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        doInit();
    }

    private void doInit(){
        activitySearchBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        activitySearchBinding.rvSeries.setHasFixedSize(true);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        seriesAdapter = new SeriesAdapter(series, this);
        activitySearchBinding.rvSeries.setAdapter(seriesAdapter);

        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null){
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    currentPage = 1;
                                    totalAvailablePages = 1;
                                    searchSeries(editable.toString());
                                }
                            });
                        }
                    }, 800);
                }else {
                    series.clear();
                    seriesAdapter.notifyDataSetChanged();
                }
            }
        });
        activitySearchBinding.rvSeries.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activitySearchBinding.rvSeries.canScrollVertically(1)){
                    if (!activitySearchBinding.inputSearch.getText().toString().isEmpty()){
                        if (currentPage < totalAvailablePages){
                            currentPage += 1;
                            searchSeries(activitySearchBinding.inputSearch.getText().toString());
                        }
                    }
                }
            }
        });
        activitySearchBinding.inputSearch.requestFocus();
    }

    private void searchSeries(String query){
        toggleLoading();
        searchViewModel.searchSeries(query, currentPage).observe(this, seriesResponse -> {
            toggleLoading();
            if (seriesResponse != null){
                totalAvailablePages = seriesResponse.getTotalPages();
                if (seriesResponse.getTvSeries() != null){
                    int oldCount = series.size();
                    series.addAll(seriesResponse.getTvSeries());
                    seriesAdapter.notifyItemRangeInserted(oldCount, series.size());
                }
            }
        });
    }

    private void toggleLoading(){
        if (currentPage == 1){
            if (activitySearchBinding.getIsLoading() != null && activitySearchBinding.getIsLoading()){
                activitySearchBinding.setIsLoading(false);
            }else {
                activitySearchBinding.setIsLoading(true);
            }
        }else {
            if (activitySearchBinding.getIsLoadingMore() != null && activitySearchBinding.getIsLoadingMore()){
                activitySearchBinding.setIsLoadingMore(false);
            }else {
                activitySearchBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onSeriesClicked(Series series) {
        Intent intent = new Intent(getApplicationContext(), SeriesDetailsActivity.class);
        intent.putExtra("series", series);
        startActivity(intent);
    }
}