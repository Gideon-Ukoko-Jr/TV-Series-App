package com.giko.tvapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.giko.tvapp.R;
import com.giko.tvapp.viewmodels.MostPopularSeriesViewModel;

public class MainActivity extends AppCompatActivity {

    private MostPopularSeriesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MostPopularSeriesViewModel.class);
        getMostPopularSeries();
    }

    private void getMostPopularSeries(){
        viewModel.getMostPopularSeries(0).observe(this, mostPopularSeriesResponse ->
                Toast.makeText(getApplicationContext(), "Total Pages: " + mostPopularSeriesResponse.getTotalPages(), Toast.LENGTH_SHORT).show()
        );
    }
}