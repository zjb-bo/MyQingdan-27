package com.qingdan.myqingdan.gui.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/10/21.
 */

public class AutoScollerViewPager extends ViewPager{
    public AutoScollerViewPager(Context context) {
        super(context);
        init(context);
    }



    public AutoScollerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /***
     * 通过反射修改viewPager的滑动速度
     * @param context
     */
    private void init(Context context) {

        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            //设置权限
            mScroller.setAccessible(true);
            Scroller scroller = new Scroller(context){
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, 2500);
                }
            };

            //反射最后一步  替换 将原来的mScroller,变成新的scroller
            mScroller.set(this,scroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复写setAdapter方法，替换原方法的adapter
     * @param adapter
     */

    @Override
    public void setAdapter(final PagerAdapter adapter) {
        super.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {

                return adapter.isViewFromObject(view,object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                return adapter.instantiateItem(container, position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                adapter.destroyItem(container, position, object);
            }
        });
        startTimer();
    }

    private void showNextPage() {
        post(new Runnable() {
            @Override
            public void run() {
                setCurrentItem(getCurrentItem()+1);
            }
        });
    }
    private Timer timer = new Timer();
    private TimerTask task;

    private void startTimer() {
        if(task == null){
            task = new TimerTask() {
                @Override
                public void run() {
                    showNextPage();
                }
            };
            timer.schedule(task,5000,5000);
        }
    }

    /**
     * 停止当前定时任务
     * task 需要为 null;
     */
    private void stopTimer(){
        if (task != null){
            task.cancel();
            task = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                stopTimer();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                startTimer();
                break;
        }

        return super.onTouchEvent(ev);
    }
}
