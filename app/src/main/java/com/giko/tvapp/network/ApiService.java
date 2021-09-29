package com.giko.tvapp.network;

import com.giko.tvapp.responses.SeriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<SeriesResponse> getMostPopularSeries(@Query("page") int page);
}
