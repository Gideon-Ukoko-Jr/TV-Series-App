package com.giko.tvapp.responses;

import com.giko.tvapp.model.SeriesDetails;
import com.google.gson.annotations.SerializedName;

public class SeriesDetailsResponse {

    @SerializedName("tvShow")
    private SeriesDetails seriesDetails;

    public SeriesDetails getSeriesDetails() {
        return seriesDetails;
    }
}
