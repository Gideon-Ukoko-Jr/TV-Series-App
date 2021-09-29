package com.giko.tvapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.giko.tvapp.repositories.MostPopularSeriesRepository;
import com.giko.tvapp.responses.SeriesResponse;

public class MostPopularSeriesViewModel extends ViewModel {

    private MostPopularSeriesRepository mostPopularSeriesRepository;

    public MostPopularSeriesViewModel(){
        mostPopularSeriesRepository = new MostPopularSeriesRepository();
    }

    public LiveData<SeriesResponse> getMostPopularSeries(int page){

        return mostPopularSeriesRepository.getMostPopularSeries(page);
    }

}
