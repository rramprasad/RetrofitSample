package com.lakeba.retrofitsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.title_textview)
    TextView title;

    @Bind(R.id.call_button)
    Button callButton;

    @Bind(R.id.result_textview)
    TextView resultTextView;

    @Bind(R.id.call_progress_bar)
    ProgressBar callProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Butter knife
        ButterKnife.bind(this);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWebService();
            }
        });


    }

    private void callWebService() {

        callProgressBar.setVisibility(View.VISIBLE);
        resultTextView.setText("");

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        APIService apiService = retrofit.create(APIService.class);

        //Call<WeatherData> weatherDataCall = apiService.fetchWeatherData("London,uk", "ENTER_YOUR_API_KEY_FROM_OPEN_WEATHER_SITE");
        Call<WeatherData> weatherDataCall = apiService.fetchWeatherData("London,uk", "6339c3c1c2c9cbdfae61993f998a56a1");

        weatherDataCall.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                Log.i(LOG_TAG,"Result => "+response.body().getName());

                resultTextView.setText("Wind Speed => "+response.body().getWind().getSpeed());

                callProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.i(LOG_TAG,"failed => "+t.getMessage());

                resultTextView.setText("failed => "+t.getMessage());

                callProgressBar.setVisibility(View.GONE);
            }
        });

    }
}
