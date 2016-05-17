package com.ykf.bishe.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by ykf on 16/3/28.
 */
public abstract class BaseFragment extends Fragment{

    protected Subscription subscription;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),null);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    protected void unsubscribe(){
        if(subscription != null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
}
