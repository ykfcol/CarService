package com.ykf.bishe.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.ui.base.BaseActivity;
import com.ykf.bishe.ui.util.App;
import com.ykf.bishe.ui.util.AppManager;
import com.ykf.bishe.ui.util.SharePreferenceManager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ykf on 16/4/6.
 */
public class EditUserInfoActivity extends BaseActivity{

    @Bind(R.id.ll_actionbar)
    RelativeLayout actionbar;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_enter)
    TextView tv_enter;
    @Bind(R.id.btn_enter)
    Button btn_enter;
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_phone)
    EditText et_phone;

    @Override
    protected int getLayoutId() {
        return R.layout.act_edit_user_information;
    }

    @Override
    protected void initData() {
        actionbar.setPadding(0,getStatusBarHeight(),0,0);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("用户基本信息");
        tv_enter.setText("跳过");
        tv_enter.setVisibility(View.VISIBLE);
        if(SharePreferenceManager.getInstance(this).getName()!=null){
            et_name.setText(SharePreferenceManager.getInstance(this).getName());
        }
        if(SharePreferenceManager.getInstance(this).getPhone()!=null){
            et_phone.setText(SharePreferenceManager.getInstance(this).getPhone());
        }
    }

    @OnClick(R.id.tv_enter)
    public void exit(){
        AppManager.getAppManager().finishActivity(LoginActivity.class);
        finish();
    }
    @OnClick(R.id.btn_enter)
    public void enter(){
        if(TextUtils.isEmpty(et_name.getText().toString().trim())||
                TextUtils.isEmpty(et_phone.getText().toString().trim())){
            Toast.makeText(this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        SharePreferenceManager.getInstance(this).setName(et_name.getText().toString().trim());
        SharePreferenceManager.getInstance(this).setPhone(et_phone.getText().toString().trim());
        Toast.makeText(this, App.getInstance().name, Toast.LENGTH_SHORT).show();
        AppManager.getAppManager().finishActivity(LoginActivity.class);
        finish();
    }
}
