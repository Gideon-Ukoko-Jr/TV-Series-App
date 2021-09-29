package com.giko.tvapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.giko.tvapp.network.ApiClient;
import com.giko.tvapp.network.ApiService;
import com.giko.tvapp.responses.SeriesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularSeriesRepository {

    private ApiService apiService;

    public MostPopularSeriesRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<SeriesResponse> getMostPopularSeries(int page){
        MutableLiveData<SeriesResponse> data = new MutableLiveData<>();
        apiService.getMostPopularSeries(page).enqueue(new Callback<SeriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<SeriesResponse> call, @NonNull Response<SeriesResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SeriesResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
