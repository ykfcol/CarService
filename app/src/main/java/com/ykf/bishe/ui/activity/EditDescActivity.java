package com.ykf.bishe.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.Desc;
import com.ykf.bishe.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ykf on 16/5/16.
 */
public class EditDescActivity extends BaseActivity{

    @Bind(R.id.et_edit_desc)
    EditText et_edit_desc;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_enter)
    TextView tv_enter;

    @Override
    protected int getLayoutId() {
        return R.layout.act_edit_desc;
    }

    @Override
    protected void initData() {
        iv_back.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_enter.setVisibility(View.VISIBLE);
        tv_title.setText("描述");
    }

    @OnClick(R.id.iv_back)
    public void back(){
        finish();
    }

    @OnClick(R.id.tv_enter)
    public void enter(){
        String desc = et_edit_desc.getText().toString().trim();
        Desc d = new Desc(desc);
        EventBus.getDefault().post(d);
        finish();
    }
}
