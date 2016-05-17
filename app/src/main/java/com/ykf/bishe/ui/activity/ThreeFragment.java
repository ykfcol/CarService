package com.ykf.bishe.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.Location;
import com.ykf.bishe.ui.adapter.LocationAdapter;
import com.ykf.bishe.ui.base.BaseFragment;
import com.ykf.bishe.ui.util.AppManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ykf on 16/4/10.
 */
public class ThreeFragment extends BaseFragment{
    @Bind(R.id.lv_address) RecyclerView lv_address;
    LocationAdapter adapter = new LocationAdapter();
    List<Location> locations = new ArrayList<Location>();
    @Override
    protected int getLayoutId() {
        return R.layout.fra_list;
    }

    @Override
    protected void initView() {
        locations.clear();
        lv_address.setLayoutManager(new LinearLayoutManager(getContext()));
        lv_address.setAdapter(adapter);
        adapter.setOnItemClickLitener(new LocationAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Location location = locations.get(position);
                EventBus.getDefault().post(location.getName());
                AppManager.getAppManager().finishActivity(MapActivity.class);
            }
        });
        Inputtips inputTips = new Inputtips(getContext(),
                new Inputtips.InputtipsListener() {
                    @Override
                    public void onGetInputtips(List<Tip> tipList, int rCode) {
                        if (rCode == 0) {// 正确返回
                            for (int i = 0; i < tipList.size(); i++) {
                                Location location = new Location();
                                location.setName(tipList.get(i).getName());
                                location.setDistrict(tipList.get(i).getDistrict());
                                location.setAdcode(tipList.get(i).getAdcode());
                                locations.add(location);
                            }
                            adapter.setLocations(locations);
                        }
                    }
                });
        try {
            inputTips.requestInputtips("4S", "杭州");// 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号
        } catch (AMapException e) {
            e.printStackTrace();
        }

    }

}
