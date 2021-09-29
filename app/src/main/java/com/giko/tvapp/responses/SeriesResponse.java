package com.giko.tvapp.responses;

import com.giko.tvapp.model.Series;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeriesResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("tv_shows")
    private List<Series> tvSeries;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Series> getTvSeries() {
        return tvSeries;
    }
}
