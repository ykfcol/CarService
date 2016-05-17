package com.ykf.bishe.model;

import com.google.gson.annotations.SerializedName;
import com.ykf.bishe.model.entity.Results;

/**
 * Created by ykf on 16/5/10.
 */
public class WeatherResult {

    public String resultcode;
    public String reason;
    public @SerializedName("result") Results results;
}
