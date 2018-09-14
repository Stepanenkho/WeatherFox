package com.qtsn.fox.test.weatherfox.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qtsn.fox.test.weatherfox.R;
import com.qtsn.fox.test.weatherfox.activity.MainActivity;
import com.qtsn.fox.test.weatherfox.pojo.CurrentWeather;
import com.qtsn.fox.test.weatherfox.service.RetrofitClient;
import com.qtsn.fox.test.weatherfox.service.WeatherService;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qtsn.fox.test.weatherfox.activity.MainActivity.DEG_TYPE;
import static com.qtsn.fox.test.weatherfox.activity.MainActivity.LOCATION;

/**
 * Fragment for current day
 */
public class CurrentFragment extends Fragment {

    /**
     * Widget
     */
    private TextView mTvPoint;
    private TextView mTvDayNTime;
    private TextView mTvSkytext;
    private ImageView mIvImage;
    private TextView mTvFeels;
    private TextView mTvHumidity;
    private TextView mTvWind;
    private TextView mTvTemp;
    private TextView mTvDegree;
    private LinearLayout mLlTemp;

    // Retrofit Service
    private WeatherService mWeatherService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            // Widget Binding
            mTvPoint = view.findViewById(R.id.tv_obs_point);
            mTvDayNTime = view.findViewById(R.id.tv_dayntime);
            mTvSkytext = view.findViewById(R.id.tv_skytext);
            mIvImage = view.findViewById(R.id.iv_image);
            mTvFeels = view.findViewById(R.id.tv_feels);
            mTvHumidity = view.findViewById(R.id.tv_humidity);
            mTvWind = view.findViewById(R.id.tv_wind);
            mTvTemp = view.findViewById(R.id.tv_temp);
            mTvDegree = view.findViewById(R.id.tv_degree);
            mLlTemp = view.findViewById(R.id.ll_temp);

            // Retrieving input location
            String location = bundle.getString(LOCATION);

            // Build Retrofit Client
            mWeatherService = RetrofitClient.getClient().create(WeatherService.class);

            // Make the call
            Call<CurrentWeather> call = mWeatherService.getCurrentWeather(location, DEG_TYPE);
            call.enqueue(new Callback<CurrentWeather>() {
                @Override
                public void onResponse(@NonNull Call<CurrentWeather> call, Response<CurrentWeather> response) {
                    init_data(response.body());
                }

                @Override
                public void onFailure(Call<CurrentWeather> call, Throwable t) {
                    if (t instanceof SocketTimeoutException){
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_retrofit), Toast.LENGTH_SHORT).show();
                    }
                    ((MainActivity) getActivity()).getProgress().setVisibility(View.GONE);
                }
            });

        }
        return view;
    }

    /**
     * Data Initialisation from API response
     * @param weather
     */
    private void init_data(CurrentWeather weather) {
        if (weather != null) {
            ((MainActivity) getActivity()).getProgress().setVisibility(View.GONE);

            mTvPoint.setText(weather.getObservationpoint());
            mTvDayNTime.setText(weather.getDay() + " " + weather.getObservationtime());
            mTvSkytext.setText(weather.getSkytext());

            mTvFeels.setText(getResources().getString(R.string.feels) + " " + weather.getFeelslike().toString() + "°" + weather.getDegType());
            mTvHumidity.setText(getResources().getString(R.string.humidity) + " " + weather.getHumidity().toString() + "%");
            mTvWind.setText(getResources().getString(R.string.wind) + " " + weather.getWinddisplay());
            mTvTemp.setText(weather.getTemperature().toString());
            Picasso.get().load(weather.getImageUrl()).into(mIvImage);
            mTvDegree.setText("°" + weather.getDegType());

            animate();
        }
    }

    /**
     * Animate Widget
     */
    private void animate() {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.item_animation_fade_in);
        anim.reset();
        mLlTemp.clearAnimation();
        mLlTemp.startAnimation(anim);
    }
}
