package com.ykf.bishe.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ykf on 16/5/16.
 */
public class MoreActivity extends BaseActivity{
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.ll_edit_info)
    LinearLayout ll_edit_info;
    @Bind(R.id.ll_ing)
    LinearLayout ll_ing;
    @Bind(R.id.ll_yuyue)
    LinearLayout ll_yuyue;



    @Override
    protected int getLayoutId() {
        return R.layout.act_more;
    }

    @Override
    protected void initData() {
        iv_back.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("我的");
    }

    @OnClick(R.id.iv_back)
    public void back(){
        finish();
    }
    @OnClick(R.id.ll_edit_info)
    public void editInfo(){
        Intent intent = new Intent(this,EditUserInfoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_ing)
    public void ing(){
        Intent intent = new Intent(this,OrderIngListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_yuyue)
    public void yuyue(){
        Intent intent = new Intent(this,OrderedListActivity.class);
        startActivity(intent);
    }
}
