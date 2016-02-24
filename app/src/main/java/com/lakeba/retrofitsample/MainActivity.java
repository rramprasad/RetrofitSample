package com.lakeba.retrofitsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callWebService();
    }

    private void callWebService() {

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<WeatherData> weatherDataCall = apiService.fetchWeatherData("London,uk", "ENTER_YOUR_API_KEY_FROM_OPEN_WEATHER_SITE");

        weatherDataCall.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                Log.i(LOG_TAG,"Result => "+response.body().getName());
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.i(LOG_TAG,"failed => "+t.getMessage());
            }
        });

    }
}
