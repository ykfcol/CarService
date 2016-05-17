package com.ykf.bishe.ui.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ykf on 16/4/6.
 */
public class BannerPagerAdapter extends PagerAdapter{

    private List<View> list;

    public BannerPagerAdapter(List<View> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager)container).addView(list.get(position));
        return list.get(position);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void finishUpdate(ViewGroup container) {

    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

    @Override
    public void startUpdate(ViewGroup container) {

    }
}
