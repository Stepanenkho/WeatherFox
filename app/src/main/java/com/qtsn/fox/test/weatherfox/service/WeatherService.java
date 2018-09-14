package com.qtsn.fox.test.weatherfox.service;

import com.qtsn.fox.test.weatherfox.pojo.CurrentWeather;
import com.qtsn.fox.test.weatherfox.pojo.ForecastWeather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("current")
    Call<CurrentWeather> getCurrentWeather(@Query("loc") String location, @Query("deg") String deg);

    @GET("forecast")
    Call<List<ForecastWeather>> getForecast(@Query("loc") String location, @Query("deg") String deg);
}
