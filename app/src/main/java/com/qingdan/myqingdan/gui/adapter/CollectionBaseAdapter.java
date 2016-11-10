package com.qingdan.myqingdan.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.ResponseCollectionDetail;
import com.qingdan.myqingdan.gui.activity.CollectionDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/4.
 */

public class CollectionBaseAdapter extends BaseAdapter {
    private List<ResponseCollectionDetail.DataBean1.CollectionsBean.BodyBean.DataBean.ArticlesBean> data;
    private LayoutInflater inflater;

    public CollectionBaseAdapter(Context context) {
        this.data = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    public void addData(ResponseCollectionDetail data, CollectionDetailActivity.CollectionViewHolder holder) {
        this.data.clear();
        holder.collectionDetailImg.setImageURI(data.getData().getCollection().getBody().getData().getFeaturedImageUrl());
        holder.collectionDetailSubtitle.setText(data.getData().getCollection().getBody().getData().getSubtitle());
        holder.collectionDetailTitle.setText(data.getData().getCollection().getBody().getData().getTitle());
        holder.collectionDetailTag.setText("专题");

        this.data.addAll(data.getData().getCollections().getBody().getData().getArticles());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.collectonsdetail_item, null);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (MyViewHolder) convertView.getTag();
        }
        ResponseCollectionDetail.DataBean1.CollectionsBean.BodyBean.DataBean.ArticlesBean collections =
                data.get(position);
        holder.collectionsDetailImg.setImageURI(collections.getFeaturedImageUrl());
        holder.collectionsDetailLikecount.setText(collections.getLikeCount()+"");
        holder.collectionsDetailLookcount.setText(collections.getHitCount()+"");
        holder.collectionsDetailTitle.setText(collections.getTitle());

        return convertView;
    }

    static class MyViewHolder {
        @BindView(R.id.collections_detail_img)
        SimpleDraweeView collectionsDetailImg;
        @BindView(R.id.collections_detail_title)
        TextView collectionsDetailTitle;
        @BindView(R.id.collections_detail_likecount)
        TextView collectionsDetailLikecount;
        @BindView(R.id.collections_detail_lookcount)
        TextView collectionsDetailLookcount;

        MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
