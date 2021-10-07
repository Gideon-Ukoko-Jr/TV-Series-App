package com.giko.tvapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        activitySeriesDetailsBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
                        activitySeriesDetailsBinding.setSeriesImageURL(
                                seriesDetailsResponse.getSeriesDetails().getImagePath()
                        );
                        activitySeriesDetailsBinding.imgSeries.setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    private void loadImageSlider(String[] sliderImages){
        activitySeriesDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activitySeriesDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        activitySeriesDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activitySeriesDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);

        setupSliderIndicators(sliderImages.length);
        activitySeriesDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setupSliderIndicators(int count){
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            activitySeriesDetailsBinding.layoutSliderIndicators.addView(indicators[i]);
        }
        activitySeriesDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position){
        int childCount = activitySeriesDetailsBinding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) activitySeriesDetailsBinding.layoutSliderIndicators.getChildAt(i);
            if (i == position){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active)
                );
            }else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive)
                );
            }
        }
    }
}