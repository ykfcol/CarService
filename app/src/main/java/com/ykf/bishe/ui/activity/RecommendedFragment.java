package com.ykf.bishe.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.Order;
import com.ykf.bishe.ui.adapter.OrderAdapter;
import com.ykf.bishe.ui.base.BaseFragment;
import com.ykf.bishe.ui.util.App;
import com.ykf.bishe.ui.util.SharePreferenceManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by ykf on 16/3/23.
 */
public class RecommendedFragment extends BaseFragment {

    private Context context;

    @Bind(R.id.tv_weather) TextView tv_more;
    @Bind(R.id.tv_register) TextView tv_register;
    @Bind(R.id.tv_login) TextView tv_login;
    @Bind(R.id.ll_login) LinearLayout ll_login;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    List<Order> orders;
    OrderAdapter orderAdapter = new OrderAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommended;
    }

    protected void initView() {
        context = this.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(orderAdapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        orderAdapter.setOnItemClickLitener(new OrderAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context,RepairDetail.class);
                intent.putExtra("area",orders.get(position).getArea());
                intent.putExtra("address",orders.get(position).getAddress());
                startActivity(intent);
            }
        });
        searchOrderList();
    }


    @Override
    public void onResume() {
        if(SharePreferenceManager.getInstance(context).getUsername()!=null&&
                !SharePreferenceManager.getInstance(context).getUsername().equals("")){
            ll_login.setVisibility(View.GONE);
        }
        super.onResume();
    }

    @OnClick({R.id.tv_login,R.id.tv_register})
    public void loginOrRegister() {
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.tv_weather)
    public void searchWeather(){
        Intent intent = new Intent(getActivity(),WeatherActivity.class);
        getActivity().startActivity(intent);
    }


    private void searchOrderList() {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.findObjects(context, new FindListener<Order>() {
            @Override
            public void onSuccess(List<Order> list) {
                orders = list;
                orderAdapter.setOrders(list);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            }
        });
    }




}
