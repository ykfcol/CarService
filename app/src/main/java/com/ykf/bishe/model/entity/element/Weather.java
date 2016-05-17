package com.ykf.bishe.model.entity.element;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ykf on 16/5/11.
 */
public class Weather {
    public @SerializedName("temperature") String temperature;
    public @SerializedName("weather") String weather;
    public @SerializedName("wind") String wind;
    public @SerializedName("week") String week;
    public @SerializedName("date") String date;
}
