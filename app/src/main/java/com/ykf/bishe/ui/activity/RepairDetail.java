package com.ykf.bishe.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.Order;
import com.ykf.bishe.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by ykf on 16/5/16.
 */
public class RepairDetail extends BaseActivity{

    @Bind(R.id.tv_area)
    TextView tv_area;
    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.iv_share)
    ImageView iv_share;
    @Override
    protected int getLayoutId() {
        return R.layout.act_repair_detail;
    }

    @Override
    protected void initData() {
        tv_title.setText("详情");
        tv_title.setVisibility(View.VISIBLE);
        tv_area.setText(getIntent().getStringExtra("area"));
        tv_address.setText(getIntent().getStringExtra("address"));
        iv_share.setVisibility(View.VISIBLE);
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
