package com.ykf.bishe.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ykf on 16/4/7.
 */
public abstract class AreaAdapter<T> extends BaseAdapter {

    public  List<T> datas;
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public abstract View getView(int i, View view, ViewGroup viewGroup);
}
