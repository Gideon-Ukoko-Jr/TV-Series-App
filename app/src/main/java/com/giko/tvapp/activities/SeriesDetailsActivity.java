package com.giko.tvapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.TextUtils;
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
                        activitySeriesDetailsBinding.setDescription(
                                String.valueOf(
                                        HtmlCompat.fromHtml(
                                                seriesDetailsResponse.getSeriesDetails().getDescription(),
                                                HtmlCompat.FROM_HTML_MODE_LEGACY
                                        )
                                )
                        );
                        activitySeriesDetailsBinding.txtDescription.setVisibility(View.VISIBLE);
                        activitySeriesDetailsBinding.txtReadMore.setVisibility(View.VISIBLE);
                        activitySeriesDetailsBinding.txtReadMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (activitySeriesDetailsBinding.txtReadMore.getText().toString().equals("Read More")){
                                    activitySeriesDetailsBinding.txtDescription.setMaxLines(Integer.MAX_VALUE);
                                    activitySeriesDetailsBinding.txtDescription.setEllipsize(null);
                                    activitySeriesDetailsBinding.txtReadMore.setText(R.string.read_less);
                                }else {
                                    activitySeriesDetailsBinding.txtDescription.setMaxLines(4);
                                    activitySeriesDetailsBinding.txtDescription.setEllipsize(TextUtils.TruncateAt.END);
                                    activitySeriesDetailsBinding.txtReadMore.setText(R.string.read_more);
                                }
                            }
                        });
                        loadBasicSeriesDetails();
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

    private void loadBasicSeriesDetails(){
        activitySeriesDetailsBinding.setSeriesName(getIntent().getStringExtra("name"));
        activitySeriesDetailsBinding.setNetworkCountry(
                getIntent().getStringExtra("network") + " (" +
                        getIntent().getStringExtra("country") + ")"
        );
        activitySeriesDetailsBinding.setStatus(getIntent().getStringExtra("status"));
        activitySeriesDetailsBinding.setStartedDate(getIntent().getStringExtra("startDate"));

        activitySeriesDetailsBinding.textName.setVisibility(View.VISIBLE);
        activitySeriesDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        activitySeriesDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        activitySeriesDetailsBinding.textStarted.setVisibility(View.VISIBLE);
    }
}