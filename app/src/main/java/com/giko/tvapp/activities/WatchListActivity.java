package com.giko.tvapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.giko.tvapp.R;
import com.giko.tvapp.databinding.ActivityWatchListBinding;
import com.giko.tvapp.viewmodels.WatchlistViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchlistViewModel watchlistViewModel;

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
    }

    private void loadWatchlist(){
        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.loadWatchlist().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(series -> {
                    activityWatchListBinding.setIsLoading(false);
                    Toast.makeText(getApplicationContext(), "Watchlist: " + series.size(), Toast.LENGTH_SHORT).show();
                })
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchlist();
    }
}