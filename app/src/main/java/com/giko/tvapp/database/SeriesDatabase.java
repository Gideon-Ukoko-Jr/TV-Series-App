package com.giko.tvapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.giko.tvapp.dao.SeriesDao;
import com.giko.tvapp.model.Series;

@Database(entities = Series.class, version = 1, exportSchema = false)
public abstract class SeriesDatabase extends RoomDatabase {

    private static SeriesDatabase seriesDatabase;

    public static synchronized SeriesDatabase getSeriesDatabase(Context context){
        if (seriesDatabase == null){
            seriesDatabase = Room.databaseBuilder(context, SeriesDatabase.class, "series_db").build();
        }
        return seriesDatabase;
    }

    public abstract SeriesDao seriesDao();
}
