package com.qingdan.myqingdan.gui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.ResposeBatching;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */

public class MainSlidePagerAdapter extends PagerAdapter{
    private List<ResposeBatching.DataBean1.SlidesBean1.BodyBean.DataBean.SlidesBean> datas;
    private List<View> views;

    public MainSlidePagerAdapter(Context context,List<ResposeBatching.DataBean1.SlidesBean1.BodyBean.DataBean.SlidesBean> datas) {
        this.datas = datas;
        views = new ArrayList<>();
        LayoutInflater lf = LayoutInflater.from(context);
        for (int i = 0; i < datas.size(); i++) {
            View view = lf.inflate(R.layout.main_slide_pageradapter,null);
            views.add(view);
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position%getCount()));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View view = views.get(position%getCount());
        container.addView(view);
        SimpleDraweeView imgView = (SimpleDraweeView) view.findViewById(R.id.main_slide_adapter);
        imgView.setImageURI(datas.get(position%getCount()).getFeaturedImageUrl());

        /****
         * view点击监听
         */
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.itemClick(position,view,datas.get(position%getCount()));
                }
            }
        });
        return view;
    }


    public void setOnItemListener(OnItemListener listener){
        this.listener = listener;
    }
    private OnItemListener listener;
    public interface OnItemListener{
        void itemClick(int position,View view,Object object);
    }
}
