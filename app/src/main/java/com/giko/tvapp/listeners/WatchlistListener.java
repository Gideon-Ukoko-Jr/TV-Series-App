package com.giko.tvapp.listeners;

import com.giko.tvapp.model.Series;

public interface WatchlistListener {
    
    void onSeriesClicked(Series series);

    void removeSeriesFromWatchlist(Series series, int position);
}
