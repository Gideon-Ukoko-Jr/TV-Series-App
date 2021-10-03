package com.giko.tvapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.giko.tvapp.R;
import com.giko.tvapp.databinding.TvShowItemContainerBinding;
import com.giko.tvapp.listeners.SeriesListener;
import com.giko.tvapp.model.Series;

import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {

    private List<Series> series;
    private LayoutInflater layoutInflater;

    private SeriesListener seriesListener;

    public SeriesAdapter(List<Series> series, SeriesListener seriesListener){
        this.series = series;
        this.seriesListener = seriesListener;
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        TvShowItemContainerBinding tvShowItemContainerBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.tv_show_item_container, parent, false
        );
        return new SeriesViewHolder(tvShowItemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesAdapter.SeriesViewHolder holder, int position) {
        holder.bindSeries(series.get(position));
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    class SeriesViewHolder extends RecyclerView.ViewHolder {

        private TvShowItemContainerBinding tvShowItemContainerBinding;

        public SeriesViewHolder(TvShowItemContainerBinding tvShowItemContainerBinding) {
            super(tvShowItemContainerBinding.getRoot());
            this.tvShowItemContainerBinding = tvShowItemContainerBinding;
        }

        public void bindSeries(Series series){
            tvShowItemContainerBinding.setTvSeries(series);
            tvShowItemContainerBinding.executePendingBindings();
            tvShowItemContainerBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    seriesListener.onSeriesClicked(series);
                }
            });
        }
    }
}
