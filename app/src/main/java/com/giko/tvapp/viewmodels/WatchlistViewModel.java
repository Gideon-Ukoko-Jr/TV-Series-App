package com.giko.tvapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.giko.tvapp.database.SeriesDatabase;
import com.giko.tvapp.model.Series;

import java.util.List;

import io.reactivex.Flowable;

public class WatchlistViewModel extends AndroidViewModel {

    private SeriesDatabase seriesDatabase;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        seriesDatabase = SeriesDatabase.getSeriesDatabase(application);
    }

    public Flowable<List<Series>> loadWatchlist(){
        return seriesDatabase.seriesDao().getWatchList();
    }
}
