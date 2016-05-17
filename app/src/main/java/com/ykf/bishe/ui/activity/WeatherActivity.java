package com.ykf.bishe.ui.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykf.bishe.ui.util.Constant;
import com.ykf.bishe.api.Network;
import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.WeatherResult;
import com.ykf.bishe.model.entity.Results;
import com.ykf.bishe.model.entity.element.Sk;
import com.ykf.bishe.model.entity.element.Today;
import com.ykf.bishe.model.entity.element.Weather;
import com.ykf.bishe.ui.adapter.WeatherAdapter;
import com.ykf.bishe.ui.base.BaseActivity;

import java.util.List;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ykf on 16/5/11.
 */
public class WeatherActivity extends BaseActivity {
    @Bind(R.id.ll_actionbar) RelativeLayout actionbar;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.weatherRv) RecyclerView weatherRv;
    @Bind(R.id.tv_temp) TextView tv_temp;
    @Bind(R.id.tv_weather) TextView tv_weather;
    @Bind(R.id.wash_index) TextView wash_index;
    @Bind(R.id.tv_title) TextView tv_title;

    List<Weather> futureWeathers;
    WeatherAdapter adapter = new WeatherAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.act_weather;
    }

    @Override
    protected void initData() {
        initActionBar();
        actionbar.setPadding(0,getStatusBarHeight(),0,0);
        weatherRv.setLayoutManager(new LinearLayoutManager(this));
        weatherRv.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        searchWeather();
    }

    private void initActionBar() {
        tv_title.setText("杭州");
        tv_title.setVisibility(View.VISIBLE);
    }

    Observer<Results> observer = new Observer<Results>(){
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(WeatherActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Results results) {
            Toast.makeText(WeatherActivity.this, results.future.get(1).temperature, Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            Sk sk = results.sk;
            Today today = results.today;
            futureWeathers = results.future;
            tv_temp.setText(sk.temp);
            tv_weather.setText(today.weather);
            wash_index.setText("洗车指数:"+today.wash_index);
            adapter.setWeathers(futureWeathers);
        }
    };


    private void searchWeather(){
        swipeRefreshLayout.setRefreshing(true);
        unsubscribe();
        subscription = Network.getWeatherApi()
                .search("2","杭州", Constant.WEATHER_APPLY_KEY)
                .map(new Func1<WeatherResult, Results>() {
                    @Override
                    public Results call(WeatherResult weatherResult) {
                        Results results = weatherResult.results;
                        return results;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
