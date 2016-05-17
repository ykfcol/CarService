package com.ykf.bishe.api;

import com.ykf.bishe.ui.util.Constant;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ykf on 16/5/10.
 */
public class Network {

    private static WeatherApi weatherApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static WeatherApi getWeatherApi(){
        if(weatherApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.WEATHER_BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            weatherApi = retrofit.create(WeatherApi.class);
        }
        return weatherApi;
    }

}
