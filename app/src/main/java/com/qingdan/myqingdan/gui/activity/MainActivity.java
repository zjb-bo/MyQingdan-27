package com.qingdan.myqingdan.gui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckedTextView;

import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.gui.adapter.MainFragmentViewApapter;
import com.qingdan.myqingdan.gui.fragment.FragmentList;
import com.qingdan.myqingdan.gui.fragment.FragmentMine;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    /*底部三个标签*/
    private CheckedTextView[] mTabs;

    /**底部三个标签ID**/
    private int[] mTabIds;
    /**ViewPager**/
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPage(0);
    }

    @Override
    protected void initBeforeSetView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    protected void initDatas() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentList());
        fragments.add(new FragmentMine());
        MainFragmentViewApapter adapter = new MainFragmentViewApapter(fragments,getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);

    }


    @Override
    protected void initViews() {
        mTabIds = new int[]{R.id.lists_item,R.id.cate_item,R.id.mine_item};
        mTabs = new CheckedTextView[mTabIds.length];
        mViewPager = findViewByIdNoCast(R.id.viewpager);
        for (int i = 0; i < mTabIds.length; i++) {
            mTabs[i] = super.findViewByIdNoCast(mTabIds[i]);
            mTabs[i].setOnClickListener(this);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                showPage(position);
            }
        });
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < mTabs.length; i++) {
            if(v == mTabs[i]){
                showPage(i);
                break;
            }
        }

    }

    /***
     * 设置当前显示的页面
     * @param index
     */
    public void showPage(int index){
        for (int i = 0; i < mTabs.length; i++) {
            if(i == index){
                mTabs[i].setChecked(true);
            }else{
                mTabs[i].setChecked(false);
            }
        }
        mViewPager.setCurrentItem(index);
    }

}
