package com.ykf.bishe.model.entity;

import com.google.gson.annotations.SerializedName;
import com.ykf.bishe.model.entity.element.Future;
import com.ykf.bishe.model.entity.element.Sk;
import com.ykf.bishe.model.entity.element.Today;
import com.ykf.bishe.model.entity.element.Weather;

import java.util.List;

/**
 * Created by ykf on 16/5/10.
 */
public class Results {

    public @SerializedName("sk") Sk sk;
    public @SerializedName("today") Today today;
    public @SerializedName("future") List<Weather> future;
}
