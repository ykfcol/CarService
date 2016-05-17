package com.ykf.bishe.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykf.bishe.carservice.R;
import com.ykf.bishe.ui.adapter.CommonFragmentAdapter;
import com.ykf.bishe.ui.base.BaseActivity;
import com.ykf.bishe.ui.util.SharePreferenceManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.ll_actionbar)
    LinearLayout actionbar;
    @Bind(R.id.tv_left)
    TextView tv_left;
    @Bind(R.id.tv_right)
    TextView tv_right;

    @Bind(R.id.iv_more)
    ImageView iv_more;

    private ArrayList<Fragment> fragments;


    @Override
    protected int getLayoutId() {return R.layout.activity_main;}

    @Override
    protected void initData() {
        actionbar.setPadding(0,getStatusBarHeight(),0,0);
        setUpTabView();
    }

    private void setUpTabView() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new RecommendedFragment());
        fragments.add(new ReserveFragment());
        CommonFragmentAdapter fragmentAdapter =
                new CommonFragmentAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setCurrentItem(0);
    }
    @OnClick({R.id.tv_left,R.id.tv_right})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_left:
                viewPager.setCurrentItem(0);
                refreshView(0);
                break;
            case R.id.tv_right:
                viewPager.setCurrentItem(1);
                refreshView(1);
                break;

        }

    }


    @OnClick(R.id.iv_more)
    public void more(){
        if(SharePreferenceManager.getInstance(this).getUsername()!=null&&
                !SharePreferenceManager.getInstance(this).getUsername().equals("")){
            startActivity(new Intent(this,MoreActivity.class));
        }else{
            Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
        }

    }



    private void refreshView(int position){
        switch (position){
            case 0:
                tv_left.setTextColor(getResources().getColor(R.color.white));
                tv_right.setTextColor(getResources().getColor(R.color.gray));
                tv_left.setBackground(getResources().getDrawable(R.drawable.shape_actionbar_left_selected));
                tv_right.setBackground(getResources().getDrawable(R.drawable.shape_actionbar_right));
                break;
            case 1:
                tv_right.setTextColor(getResources().getColor(R.color.white));
                tv_left.setTextColor(getResources().getColor(R.color.gray));
                tv_left.setBackground(getResources().getDrawable(R.drawable.shape_actionbar_left));
                tv_right.setBackground(getResources().getDrawable(R.drawable.shape_actionbar_right_selected));
                break;

        }
    }

    /*protected void setTabView() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        final int[] tabIcons = {R.drawable.ic_recommended};
        FragmentPagerItems pages = FragmentPagerItems.with(this)
                .add(R.string.recommended,RecommendedFragment.class)
                .add(R.string.recommended,RecommendedFragment.class)
                .add(R.string.recommended,RecommendedFragment.class)
                .create();
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                pages
        );
        viewPager.setOffscreenPageLimit(pages.size());
        viewPager.setAdapter(adapter);
        viewPageTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View view = inflater.inflate(R.layout.custom_tab_icon,container,false);
                ImageView icon = (ImageView) view.findViewById(R.id.iv_icon);
                icon.setBackgroundResource(R.drawable.ic_recommended);
                return view;
            }
        });
        viewPageTab.setViewPager(viewPager);

    }*/

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            viewPager.setCurrentItem(position);
            refreshView(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
