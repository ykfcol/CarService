package com.ykf.bishe.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.Order;
import com.ykf.bishe.ui.adapter.OrderListAdapter;
import com.ykf.bishe.ui.base.BaseActivity;
import com.ykf.bishe.ui.util.SharePreferenceManager;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by ykf on 16/5/16.
 */
public class OrderIngListActivity extends BaseActivity{


    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    List<Order> orders;
    OrderListAdapter orderAdapter = new OrderListAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.act_order_ing;
    }

    @Override
    protected void initData() {
        iv_back.setVisibility(View.VISIBLE);
        tv_title.setText("预约列表");
        tv_title.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        orderAdapter.setOnItemClickLitener(new OrderListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Order order = orders.get(position);
                Intent intent = new Intent(OrderIngListActivity.this,OrderDetailActivity.class);
                intent.putExtra("objectId",order.getObjectId());
                intent.putExtra("order","0");
                intent.putExtra("area",order.getArea());
                intent.putExtra("address",order.getAddress());
                intent.putExtra("time",order.getTime());
                intent.putExtra("name",order.getName());
                intent.putExtra("phone",order.getPhone());
                intent.putExtra("isSafe",order.getIsSafe());
                intent.putExtra("price",order.getPrice());
                intent.putExtra("desc",order.getDesc());
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        searchOrderList();
        super.onResume();
    }

    @OnClick(R.id.iv_back)
    public void back(){
        finish();
    }

    private void searchOrderList() {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereEqualTo("status", "0");
        query.addWhereEqualTo("username", SharePreferenceManager.getInstance(this).getUsername());
        query.findObjects(this, new FindListener<Order>() {
            @Override
            public void onSuccess(List<Order> list) {
                orders = list;
                orderAdapter.setOrders(list);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(OrderIngListActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
