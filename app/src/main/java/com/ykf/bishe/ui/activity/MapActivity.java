package com.ykf.bishe.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ykf.bishe.carservice.R;
import com.ykf.bishe.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ykf on 16/4/6.
 */
public class MapActivity extends BaseActivity implements
        LocationSource,AMapLocationListener,TextWatcher,PoiSearch.OnPoiSearchListener,AMap.OnMarkerClickListener {

    @Bind(R.id.ll_actionbar) RelativeLayout actionbar;
    @Bind(R.id.map) MapView mapView;
    @Bind(R.id.tab) SmartTabLayout tab;
    @Bind(R.id.viewPager) ViewPager viewPager;
    @Bind(R.id.iv_back) ImageView iv_back;
    @Bind(R.id.tv_key_word) AutoCompleteTextView keyword;
    @Bind(R.id.iv_search) ImageView iv_search;

    private String keyWord = "";// 要输入的poi搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条
    private PoiResult poiResult; // poi返回的结果
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private Dialog dialog;


    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mLocationClient = null;


    @Override
    protected void initData() {
        initActionBar();
        actionbar.setPadding(0, getStatusBarHeight(), 0, 0);
    }

    private void initActionBar() {
        iv_back.setVisibility(View.VISIBLE);
        keyword.setVisibility(View.VISIBLE);
        iv_search.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        init();
        initTabView();
        keyword.addTextChangedListener(this);
    }

    private void init() {
        if(aMap == null){
            aMap = mapView.getMap();
            aMap.setLocationSource(this);// 设置定位监听
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            // 设置定位的类型为定位模式，参见类AMap。
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
            aMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_map;
    }

    private void initLocationOption() {
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    private void initTabView() {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
            getSupportFragmentManager(), FragmentPagerItems.with(this)
            .add(getString(R.string.quanbu), OneFragment.class)
            .add(getString(R.string.repair), TwoFragment.class)
            .add(R.string.s, ThreeFragment.class)
            .add(R.string.qita, FourFragment.class)
            .create());
        viewPager.setAdapter(adapter);
        tab.setViewPager(viewPager);

    }

    @OnClick({R.id.iv_back,R.id.iv_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
                keyWord = keyword.getText().toString();
                doSearchQuery();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(mLocationClient != null){
            mLocationClient.onDestroy();
        }

    }

    //激活定位
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationClient.setLocationListener(this);
            mLocationOption = new AMapLocationClientOption();
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                mListener.onLocationChanged(aMapLocation);
                Log.e("city",aMapLocation.getCity());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String newText = charSequence.toString().trim();
        Inputtips inputTips = new Inputtips(MapActivity.this,
                new Inputtips.InputtipsListener() {
                    @Override
                    public void onGetInputtips(List<Tip> tipList, int rCode) {
                        if (rCode == 0) {// 正确返回
                            List<String> listString = new ArrayList<String>();
                            for (int i = 0; i < tipList.size(); i++) {
                                listString.add(tipList.get(i).getName());
                            }
                            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                                    getApplicationContext(),
                                    R.layout.route_inputs, listString);
                            keyword.setAdapter(aAdapter);
                            aAdapter.notifyDataSetChanged();
                        }
                    }
                });
        try {
            inputTips.requestInputtips(newText, "杭州");// 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号

        } catch (AMapException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    protected void doSearchQuery() {
        showProgressDialog();// 显示进度框
        query = new PoiSearch.Query(keyWord, "", "杭州");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        dissmissProgressDialog();// 隐藏对话框
        if (i == 0) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    this.poiResult = poiResult;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    Log.e("sss",poiItems.get(0).toString());
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        /*showSuggestCity(suggestionCities);*/
                    } else {
                        /*ToastUtil.show(PoiKeywordSearchActivity.this,
                                R.string.no_result);*/
                    }
                }
            } else {
                /*ToastUtil.show(PoiKeywordSearchActivity.this,
                        R.string.no_result);*/
            }
        } else if (i == 27) {
            /*ToastUtil.show(PoiKeywordSearchActivity.this,
                    R.string.error_network);*/
        } else if (i == 32) {
            /*ToastUtil.show(PoiKeywordSearchActivity.this, R.string.error_key);*/
        } else {
            /*ToastUtil.show(PoiKeywordSearchActivity.this,
                    getString(R.string.error_other) + rCode);*/
        }
    }
    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + keyWord);
        progDialog.show();
    }
    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("选择  "+keyword.getText().toString()+"?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EventBus.getDefault().post(keyword.getText().toString());
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
        dialog.show();
        return false;
    }


}
