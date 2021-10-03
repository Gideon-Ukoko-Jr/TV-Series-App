package com.giko.tvapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.giko.tvapp.network.ApiClient;
import com.giko.tvapp.network.ApiService;
import com.giko.tvapp.responses.SeriesDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesDetailsRepository {

    private ApiService apiService;

    public SeriesDetailsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<SeriesDetailsResponse> getSeriesDetails(String seriesId){
        MutableLiveData<SeriesDetailsResponse> data = new MutableLiveData<>();

        apiService.getSeriesDetails(seriesId).enqueue(new Callback<SeriesDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<SeriesDetailsResponse> call, @NonNull Response<SeriesDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SeriesDetailsResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
