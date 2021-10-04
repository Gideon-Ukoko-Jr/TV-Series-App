package com.giko.tvapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.giko.tvapp.R;
import com.giko.tvapp.adapters.ImageSliderAdapter;
import com.giko.tvapp.databinding.ActivitySeriesDetailsBinding;
import com.giko.tvapp.viewmodels.SeriesDetailsViewModel;

public class SeriesDetailsActivity extends AppCompatActivity {

    private ActivitySeriesDetailsBinding activitySeriesDetailsBinding;
    private SeriesDetailsViewModel seriesDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySeriesDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_series_details);
        doInit();
    }

    private void doInit(){
        seriesDetailsViewModel = new ViewModelProvider(this).get(SeriesDetailsViewModel.class);
        getSeriesDetails();
    }

    private void getSeriesDetails(){
        activitySeriesDetailsBinding.setIsLoading(true);

        String seriesId = String.valueOf(getIntent().getIntExtra("id", -1));
        seriesDetailsViewModel.getSeriesDetails(seriesId).observe(
                this, seriesDetailsResponse -> {
                    activitySeriesDetailsBinding.setIsLoading(false);
                    if (seriesDetailsResponse.getSeriesDetails() != null){
                        if (seriesDetailsResponse.getSeriesDetails().getPictures() != null){
                            loadImageSlider(seriesDetailsResponse.getSeriesDetails().getPictures());
                        }
                    }
                }
        );
    }

    private void loadImageSlider(String[] sliderImages){
        activitySeriesDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activitySeriesDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        activitySeriesDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activitySeriesDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
    }
}