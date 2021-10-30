package com.giko.tvapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.giko.tvapp.repositories.SearchSeriesRepository;
import com.giko.tvapp.responses.SeriesResponse;

public class SearchViewModel extends ViewModel {

    private SearchSeriesRepository searchSeriesRepository;

    public SearchViewModel(){
        searchSeriesRepository = new SearchSeriesRepository();
    }

    public LiveData<SeriesResponse> searchSeries(String query, int page){
        return searchSeriesRepository.searchSeries(query, page);
    }
}
