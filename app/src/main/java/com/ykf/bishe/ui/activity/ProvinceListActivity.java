package com.ykf.bishe.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.ui.adapter.ProvinceWheelAdapter;
import com.ykf.bishe.ui.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by ykf on 16/4/7.
 */
public class ProvinceListActivity extends BaseActivity{

    Context context = ProvinceListActivity.this;
    @Bind(R.id.lv_province) ListView lv_province;
    @Bind(R.id.ll_actionbar) RelativeLayout actionbar;


    ProvinceWheelAdapter adapter;
    private final int[] ARRAY_CITY = new int[] { R.array.beijin_province_item,
            R.array.heibei_province_item, R.array.shandong_province_item,
            R.array.shanghai_province_item, R.array.guangdong_province_item,
            R.array.anhui_province_item, R.array.fujian_province_item,
            R.array.gansu_province_item, R.array.guangxi_province_item,
            R.array.guizhou_province_item, R.array.hainan_province_item,
            R.array.henan_province_item, R.array.heilongjiang_province_item,
            R.array.hubei_province_item, R.array.hunan_province_item,
            R.array.jilin_province_item, R.array.jiangsu_province_item,
            R.array.jiangxi_province_item, R.array.liaoning_province_item,
            R.array.neimenggu_province_item, R.array.ningxia_province_item,
            R.array.qinghai_province_item, R.array.shanxi1_province_item,
            R.array.shanxi2_province_item, R.array.sichuan_province_item,
            R.array.tianjin_province_item, R.array.xizang_province_item,
            R.array.xinjiang_province_item, R.array.yunnan_province_item,
            R.array.zhejiang_province_item, R.array.chongqing_province_item,
            R.array.taiwan_province_item, R.array.hongkong_province_item,
            R.array.aomen_province_item };

    @Override
    protected int getLayoutId() {
        return R.layout.act_province_list;
    }

    @Override
    protected void initData() {
        actionbar.setPadding(0,getStatusBarHeight(),0,0);
        adapter = new ProvinceWheelAdapter(this);
        lv_province.setAdapter(adapter);
        lv_province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context,CityListActivity.class);
                intent.putExtra("provinceId",ARRAY_CITY[i]);
                startActivity(intent);
            }
        });
    }
}
