package com.qingdan.myqingdan.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cjj.MaterialRefreshLayout;

/**
 * Created by Administrator on 2016/11/8.
 */

public class MyMaterialRefushLayout extends MaterialRefreshLayout {
    public MyMaterialRefushLayout(Context context) {
        super(context);
    }

    public MyMaterialRefushLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMaterialRefushLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private boolean enable = true;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!enable)return false;
        return super.onInterceptTouchEvent(ev);
    }
    public void setEablePull(boolean enable){
        this.enable = enable;
    }
}
