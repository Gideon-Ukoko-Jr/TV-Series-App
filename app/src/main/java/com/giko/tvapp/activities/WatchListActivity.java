package com.giko.tvapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.giko.tvapp.R;
import com.giko.tvapp.adapters.WatchlistAdapter;
import com.giko.tvapp.databinding.ActivityWatchListBinding;
import com.giko.tvapp.listeners.WatchlistListener;
import com.giko.tvapp.model.Series;
import com.giko.tvapp.viewmodels.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchlistListener {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchlistViewModel watchlistViewModel;
    private WatchlistAdapter watchlistAdapter;
    private List<Series> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        doInit();
    }

    private void doInit(){
        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        activityWatchListBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        watchlist = new ArrayList<>();
    }

    private void loadWatchlist(){
        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.loadWatchlist().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(series -> {
                    activityWatchListBinding.setIsLoading(false);
                    if (watchlist.size() > 0){
                        watchlist.clear();
                    }
                    watchlist.addAll(series);
                    watchlistAdapter = new WatchlistAdapter(watchlist, this);
                    activityWatchListBinding.rvWatchlist.setAdapter(watchlistAdapter);
                    activityWatchListBinding.rvWatchlist.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();
                })
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchlist();
    }

    @Override
    public void onSeriesClicked(Series series) {
        Intent intent = new Intent(getApplicationContext(), SeriesDetailsActivity.class);
        intent.putExtra("series", series);
        startActivity(intent);
    }

    @Override
    public void removeSeriesFromWatchlist(Series series, int position) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.removeSeriesFromWatchlist(series)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    watchlist.remove(position);
                    watchlistAdapter.notifyItemRemoved(position);
                    watchlistAdapter.notifyItemChanged(position, watchlistAdapter.getItemCount());
                    compositeDisposable.dispose();
                }));
    }
}