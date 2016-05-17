package com.ykf.bishe.api;

import com.ykf.bishe.model.WeatherResult;
import com.ykf.bishe.model.entity.Results;
import com.ykf.bishe.model.entity.element.Sk;
import com.ykf.bishe.model.entity.element.Weather;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ykf on 16/5/10.
 */
public interface WeatherApi {
    @GET("index")
    Observable<WeatherResult> search(@Query("format")String format, @Query("cityname")String city, @Query("key") String key);
}
