package com.qtsn.fox.test.weatherfox.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.qtsn.fox.test.weatherfox.R;
import com.qtsn.fox.test.weatherfox.activity.MainActivity;
import com.qtsn.fox.test.weatherfox.adapter.ForecastAdapter;
import com.qtsn.fox.test.weatherfox.pojo.ForecastWeather;
import com.qtsn.fox.test.weatherfox.service.RetrofitClient;
import com.qtsn.fox.test.weatherfox.service.WeatherService;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qtsn.fox.test.weatherfox.activity.MainActivity.DEG_TYPE;
import static com.qtsn.fox.test.weatherfox.activity.MainActivity.LOCATION;

/**
 * Fragment for next 3 days
 */
public class ForecastFragment extends Fragment {

    /**
     * Widget
     */
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Retrofit Service
    private WeatherService mWeatherService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            // RecyclerView Init
            mRecyclerView = view.findViewById(R.id.rv_forecast_list);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            LayoutAnimationController anim = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
            mRecyclerView.setLayoutAnimation(anim);

            // Retrieving input location
            String location = bundle.getString(LOCATION);

            // Build Retrofit Client
            mWeatherService = RetrofitClient.getClient().create(WeatherService.class);

            // Make the call
            Call<List<ForecastWeather>> call = mWeatherService.getForecast(location, DEG_TYPE);
            call.enqueue(new Callback<List<ForecastWeather>>() {
                @Override
                public void onResponse(Call<List<ForecastWeather>> call, Response<List<ForecastWeather>> response) {
                    init_data(response.body());
                }

                @Override
                public void onFailure(Call<List<ForecastWeather>> call, Throwable t) {
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
     * Data Initialisation on RecyclerView
     * @param weathers
     */
    private void init_data(List<ForecastWeather> weathers) {
        if (weathers != null && weathers.size() > 0) {
            ((MainActivity) getActivity()).getProgress().setVisibility(View.GONE);
            mAdapter = new ForecastAdapter(weathers);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
