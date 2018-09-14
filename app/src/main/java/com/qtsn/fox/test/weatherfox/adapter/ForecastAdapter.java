package com.qtsn.fox.test.weatherfox.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qtsn.fox.test.weatherfox.R;
import com.qtsn.fox.test.weatherfox.pojo.ForecastWeather;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<ForecastWeather> mDataset;
    private static final String URL_GIF = "http://blob.weather.microsoft.com/static/weather4/en-us/law/";
    private static final String EXTENSION_GIF = ".gif";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvDay;
        private ImageView mIvImage;
        private TextView mTvLow;
        private TextView mTvHigh;

        public ViewHolder(View v) {
            super(v);
            mTvDay = v.findViewById(R.id.tv_day_forecast);
            mIvImage = v.findViewById(R.id.iv_image_forecast);
            mTvLow = v.findViewById(R.id.tv_low_forecast);
            mTvHigh = v.findViewById(R.id.tv_high_forecast);
        }
    }

    public ForecastAdapter(List<ForecastWeather> mDateset) {
        this.mDataset = mDateset;
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder holder, int position) {
        ForecastWeather weather = mDataset.get(position);

        holder.mTvDay.setText(weather.getShortday());
        Picasso.get().load(URL_GIF + weather.getSkycodeday() + EXTENSION_GIF).into(holder.mIvImage);
        holder.mTvLow.setText(weather.getLow().toString() + "°" + weather.getDegType());
        holder.mTvHigh.setText(weather.getHigh().toString() + "°" + weather.getDegType());
    }

    @Override
    public int getItemCount() {

        return mDataset != null ? mDataset.size() : 0;
    }
}
