package com.ykf.bishe.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.ykf.bishe.carservice.R;
import com.ykf.bishe.ui.util.AppManager;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by ykf on 16/3/23.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Subscription subscription;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setStatusBarColor();
        initData();
    }

    protected abstract int getLayoutId();
    protected abstract void initData();


    //获取状态栏高度
    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //设置状态栏颜色
    protected void setStatusBarColor(){
        SystemBarTintManager tintManager=new SystemBarTintManager(this);
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        tintManager.setStatusBarTintEnabled(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    protected void unsubscribe(){
        if(subscription != null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
