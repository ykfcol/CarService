package com.ykf.bishe.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.model.entity.element.User;
import com.ykf.bishe.ui.base.BaseActivity;
import com.ykf.bishe.ui.util.App;
import com.ykf.bishe.ui.util.Constant;
import com.ykf.bishe.ui.util.SharePreferenceManager;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by ykf on 16/4/6.
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.btn_register)
    Button btn_register;
    @Bind(R.id.et_password)
    EditText et_password;
    @Override
    protected int getLayoutId() {
        return R.layout.act_login;
    }

    @Override
    protected void initData() {
        Bmob.initialize(this, Constant.BMOB_KEY);
    }

    @OnClick(R.id.btn_register)
    public void register(){
        if(TextUtils.isEmpty(et_username.getText().toString().trim())||
                TextUtils.isEmpty(et_password.getText().toString().trim())){
            Toast.makeText(this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User();
        user.setUsername(et_username.getText().toString().trim());
        user.setPassword(et_password.getText().toString().trim());
        save(user);

    }
    public void save(User user){
        user.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                App.getInstance().username = et_username.getText().toString().trim();
                Intent intent = new Intent(LoginActivity.this,EditUserInfoActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.btn_login)
    public void login(){
        if(TextUtils.isEmpty(et_username.getText().toString().trim())||
                TextUtils.isEmpty(et_password.getText().toString().trim())){
            Toast.makeText(this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        final BmobQuery<User> bmobQuery	 = new BmobQuery<User>();
        bmobQuery.addWhereEqualTo("username",et_username.getText().toString().trim());
        bmobQuery.addWhereEqualTo("password",et_password.getText().toString().trim());
        bmobQuery.findObjects(this, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if(list.size() == 0){
                    Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                }else{
                    SharePreferenceManager.getInstance(LoginActivity.this).setUsername(et_username.getText().toString());
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
