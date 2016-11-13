package com.qingdan.myqingdan.gui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qingdan.myqingdan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/13.
 */

public class MyViewPagerAdapter<T> extends PagerAdapter{
    private List<View> datas;
    private List<String> url;
    public MyViewPagerAdapter(List<String> url, Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        datas = new ArrayList<>();
        for (int i = 0; i < url.size(); i++) {
            View view = inflater.inflate(R.layout.things_item,null);
            SimpleDraweeView img = (SimpleDraweeView) view.findViewById(R.id.things_img);
            img.setImageURI(url.get(i));
            datas.add(view);
        }
    }

    @Override
    public int getCount() {

        return  datas.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(datas.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = datas.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
