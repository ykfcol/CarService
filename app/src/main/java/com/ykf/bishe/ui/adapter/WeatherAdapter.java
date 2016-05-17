package com.ykf.bishe.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.Weather;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ykf on 16/5/11.
 */
public class WeatherAdapter extends RecyclerView.Adapter{

    List<Weather> weathers;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather,parent,false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WeatherViewHolder weatherViewHolder = (WeatherViewHolder) holder;
        Weather weather = weathers.get(position);
        weatherViewHolder.tv_date.setText(weather.date+"  "+weather.week);
        weatherViewHolder.tv_temp.setText(weather.temperature);
        weatherViewHolder.tv_weather.setText(weather.weather);
        weatherViewHolder.tv_wind.setText(weather.wind);
    }

    @Override
    public int getItemCount() {
        return weathers == null ? 0 : weathers.size();
    }
    public void setWeathers(List<Weather> weathers){
        this.weathers = weathers;
        notifyDataSetChanged();
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_date) TextView tv_date;
        @Bind(R.id.tv_weather) TextView tv_weather;
        @Bind(R.id.tv_temp) TextView tv_temp;
        @Bind(R.id.tv_wind) TextView tv_wind;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
