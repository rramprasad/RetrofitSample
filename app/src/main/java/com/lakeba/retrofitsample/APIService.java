package com.lakeba.retrofitsample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Lakeba
 */
public interface APIService {

    @GET("weather")
    Call<WeatherData> fetchWeatherData(@Query("q") String query,@Query("appid") String appId);
}
