package com.ykf.bishe.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.Address;
import com.ykf.bishe.ui.adapter.CityWheelAdapter;
import com.ykf.bishe.ui.base.BaseActivity;
import com.ykf.bishe.ui.util.AppManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * Created by ykf on 16/4/7.
 */
public class CityListActivity extends BaseActivity{
    @Bind(R.id.lv_city)
    ListView lv_city;
    @Bind(R.id.ll_actionbar)
    RelativeLayout actionbar;

    CityWheelAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.act_city_list;
    }

    @Override
    protected void initData() {
        actionbar.setPadding(0,getStatusBarHeight(),0,0);
        Intent intent = getIntent();
        int i = intent.getIntExtra("provinceId",0);
        adapter = new CityWheelAdapter(this,i);
        lv_city.setAdapter(adapter);
        lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Address address = new Address();
                address.setProvince("  ");
                address.setCity(adapter.datas.get(i));
                EventBus.getDefault().post(address);
                finish();
                AppManager.getAppManager().finishActivity(ProvinceListActivity.class);
            }
        });
    }
}
