package com.ykf.bishe.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.Order;
import com.ykf.bishe.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.listener.DeleteListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by ykf on 16/5/16.
 */
public class OrderDetailActivity extends BaseActivity{
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_area)
    TextView tv_area;
    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    @Bind(R.id.tv_repair_time)
    TextView tv_time;
    @Bind(R.id.tv_is_safe)
    TextView tv_is_safe;
    @Bind(R.id.tv_price)
    TextView tv_price;
    @Bind(R.id.tv_desc)
    TextView tv_desc;
    @Bind(R.id.btn_commit)
    Button btn_commit;
    @Bind(R.id.iv_share)
    ImageView iv_share;
    @Override
    protected int getLayoutId() {
        return R.layout.act_order_detail;
    }

    @Override
    protected void initData() {
        iv_share.setVisibility(View.VISIBLE);
        tv_title.setText("订单详情");
        tv_title.setVisibility(View.VISIBLE);
        iv_back.setVisibility(View.VISIBLE);
        if(getIntent().getStringExtra("order").equals("1")){
            btn_commit.setVisibility(View.GONE);
        }
        tv_area.setText(getIntent().getStringExtra("area"));
        tv_address.setText(getIntent().getStringExtra("address"));
        tv_name.setText(getIntent().getStringExtra("name"));
        tv_phone.setText(getIntent().getStringExtra("phone"));
        tv_time.setText(getIntent().getStringExtra("time"));
        if(getIntent().getStringExtra("isSafe").equals("0")){
            tv_is_safe.setText("无");
        }else{
            tv_is_safe.setText("有");
        }
        tv_price.setText(getIntent().getStringExtra("price"));
        tv_desc.setText(getIntent().getStringExtra("desc"));
    }

    Dialog dialog;
    @OnClick(R.id.btn_commit)
    public void commit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定取消订单?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
        dialog.show();

    }
    public void delete(){
        Order order = new Order();
        order.setObjectId(getIntent().getStringExtra("objectId"));
        order.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(OrderDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(OrderDetailActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void back(){
        finish();
    }


    @OnClick(R.id.iv_share)
    public void share(){
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setText(tv_address.getText().toString());
        oks.show(this);
    }


}