package com.qingdan.myqingdan.gui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qingdan.myqingdan.R;

/**
 * Created by Administrator on 2016/10/20.
 */

public class PagerDotIndctor {

    private LayoutInflater lf;
    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private int rDotIdNol;
    private int rDotIdChecked;

    public PagerDotIndctor(Context context, final LinearLayout linearLayout, ViewPager viewPager) {
        this.lf = LayoutInflater.from(context);
        this.linearLayout = linearLayout;
        this.viewPager = viewPager;
        rDotIdChecked = R.drawable.home_page_controls_hl;
        rDotIdNol = R.drawable.home_page_controls_nor;
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(linearLayout.getChildCount() != 0){position %= linearLayout.getChildCount();}
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    View views = linearLayout.getChildAt(i);
                    ImageView view = (ImageView) views.findViewById(R.id.imageView_indicator_dot);
                    if(i == position){
                        view.setImageResource(rDotIdChecked);
                    }else{
                        view.setImageResource(rDotIdNol);
                    }
                }
            }
        });
    }

    public void setDotId(int rDotIdNol,int rDotIdChecked){
        this.rDotIdChecked = rDotIdChecked;
        this.rDotIdNol = rDotIdNol;
    }

    /**
     * 设置几个点
     * @param num
     */
    public void setDot(int num){
        linearLayout.removeAllViews();
        for (int i = 0; i < num; i++) {
            View views = lf.inflate(R.layout.subview_indicator_dots,null);
            ImageView view = (ImageView) views.findViewById(R.id.imageView_indicator_dot);

            linearLayout.addView(views);
            if(i == 0){
                view.setImageResource(rDotIdChecked);
            }else{
                view.setImageResource(rDotIdNol);
            }
        }
    }
}
