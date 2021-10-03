package com.giko.tvapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.giko.tvapp.repositories.SeriesDetailsRepository;
import com.giko.tvapp.responses.SeriesDetailsResponse;

public class SeriesDetailsViewModel extends ViewModel {

    private SeriesDetailsRepository seriesDetailsRepository;

    public SeriesDetailsViewModel(){
        seriesDetailsRepository = new SeriesDetailsRepository();
    }

    public LiveData<SeriesDetailsResponse> getSeriesDetails(String seriesId){
        return seriesDetailsRepository.getSeriesDetails(seriesId);
    }
}
