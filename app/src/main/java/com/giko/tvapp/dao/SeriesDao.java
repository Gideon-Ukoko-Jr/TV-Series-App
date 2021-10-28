package com.giko.tvapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.giko.tvapp.model.Series;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface SeriesDao {

    @Query("SELECT * FROM series")
    Flowable<List<Series>> getWatchList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList(Series series);

    @Delete
    Completable removeFromWatchList(Series series);

}
