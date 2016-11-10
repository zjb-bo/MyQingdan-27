package com.qingdan.myqingdan.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.ResponseRelatedArticles;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/4.
 */

public class MyGridViewBaseAdapter extends BaseAdapter {
    private List<ResponseRelatedArticles.DataBean.ArticlesBean> relatedArticles;
    private LayoutInflater inflater;

    public MyGridViewBaseAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        relatedArticles = new ArrayList<>();
    }

    public void addData(List<ResponseRelatedArticles.DataBean.ArticlesBean> relatedArticles) {
        this.relatedArticles.clear();
        this.relatedArticles.addAll(relatedArticles);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return relatedArticles.size();
    }

    @Override
    public Object getItem(int position) {
        return relatedArticles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyBaseViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.relactiedarticle, null);
            holder = new MyBaseViewHolder(convertView);
            //自己设置回调监听
//            holder.setPosition(position);
            convertView.setTag(holder);
        }else{
            holder = (MyBaseViewHolder) convertView.getTag();
        }


        ResponseRelatedArticles.DataBean.ArticlesBean article
                = relatedArticles.get(position);
        holder.relactiedArticlImg.setImageURI(article.getFeaturedImageUrl());
        holder.relactiedArticlText.setText(article.getTitle());

        return convertView;
    }

    public class MyBaseViewHolder {
        @BindView(R.id.relactied_articl_img)
        SimpleDraweeView relactiedArticlImg;
        @BindView(R.id.relactied_articl_text)
        TextView relactiedArticlText;

//        public int getPosition() {
//            return position;
//        }
//
//        public void setPosition(int position) {
//            this.position = position;
//        }
//
//        int position;

        MyBaseViewHolder(View view) {
            ButterKnife.bind(this, view);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener != null){
//                        listener.myItemClick(((MyBaseViewHolder)v.getTag()).getPosition(),v);
//                    }
//                }
//            });
        }
    }

    /**
     * 不使用GridView 自带的监听，自己在adpter里面设置回调监听
     */
//    private setMyOnItem listener;
//    public void setMyOnItemClickListener(setMyOnItem listener){
//        this.listener = listener;
//    };
//    public interface setMyOnItem{
//        void myItemClick(int positon,View view);
//    }




}
