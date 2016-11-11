package com.qingdan.myqingdan.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/11/10.
 */

public class MyScorllView extends ScrollView {
    public MyScorllView(Context context) {
        super(context);
    }

    public MyScorllView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScorllView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener!= null){
            listener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    /***
     * 是否滚到顶部
     * @return
     */
    public boolean isTop(){
        return getScrollY() <= 0;
    }

    public boolean isBottom(){
        return getScrollY() == getChildAt(getChildCount()-1).getBottom()+getPaddingBottom()-getHeight();
    }

    private OnMyScrollViewListener listener;
    public void setOnMyScrollViewListener(OnMyScrollViewListener listener){
        this.listener = listener;
    }
    public interface OnMyScrollViewListener{
        void onScrollChanged(int x, int y, int oldx, int oldy);
    }
}

