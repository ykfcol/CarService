package com.ykf.bishe.ui.util;

import com.ykf.bishe.model.WeatherResult;
import com.ykf.bishe.model.entity.Results;
import com.ykf.bishe.model.entity.element.Weather;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by ykf on 16/5/11.
 */
public class WeatherResultToItem implements Func1<WeatherResult,List<Weather>>{

    private static WeatherResultToItem INSTANCE = new WeatherResultToItem();
    public static WeatherResultToItem getInstance(){
        return INSTANCE;
    }

    private WeatherResultToItem(){

    }
    @Override
    public List<Weather> call(WeatherResult weatherResult) {
        return null;
    }
}
