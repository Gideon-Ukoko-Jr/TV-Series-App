package com.giko.tvapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.giko.tvapp.database.SeriesDatabase;
import com.giko.tvapp.model.Series;
import com.giko.tvapp.repositories.SeriesDetailsRepository;
import com.giko.tvapp.responses.SeriesDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class SeriesDetailsViewModel extends AndroidViewModel {

    private SeriesDetailsRepository seriesDetailsRepository;
    private SeriesDatabase seriesDatabase;

    public SeriesDetailsViewModel(@NonNull Application application) {
        super(application);
        seriesDetailsRepository = new SeriesDetailsRepository();
        seriesDatabase = SeriesDatabase.getSeriesDatabase(application);
    }

    public LiveData<SeriesDetailsResponse> getSeriesDetails(String seriesId){
        return seriesDetailsRepository.getSeriesDetails(seriesId);
    }

    public Completable addToWatchlist(Series series){
        return seriesDatabase.seriesDao().addToWatchList(series);
    }

    public Flowable<Series> getSeriesFromWatchlist(String seriesId){
        return seriesDatabase.seriesDao().getSeriesFromWatchList(seriesId);
    }

    public Completable removeSeriesFromWatchlist(Series series){
        return seriesDatabase.seriesDao().removeFromWatchList(series);
    }
}
