package com.qingdan.myqingdan.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.RankingSortData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bobo on 2016/11/9.
 */

public class RankingSortBaseAdapter extends BaseAdapter {
    private List<RankingSortData.DataBean.ThingsBean> list;
    private LayoutInflater inflater;

    public RankingSortBaseAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }

    public void addData(List<RankingSortData.DataBean.ThingsBean>  list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.list.clear();
        notifyDataSetChanged();
    }

    public void setData(List<RankingSortData.DataBean.ThingsBean>  list){
        clearData();
        addData(list);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RankingSortViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ranking_sort_item, null);
            holder = new RankingSortViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RankingSortViewHolder) convertView.getTag();
        }
        RankingSortData.DataBean.ThingsBean thingsBean = list.get(position);

        holder.imgItemRankingThingCover.setImageURI(thingsBean.getFeaturedImageUrl());
        holder.tvItemRankingThingName.setText(thingsBean.getFullName().split(" ")[0]);
        holder.tvItemRankingThingTitle.setText(thingsBean.getName());
        holder.tvScoreNum.setText(holder.tvScoreNum.getContext().getString
                (R.string.text,thingsBean.getRatingScore()+"",thingsBean.getReviewCount()));
        holder.ratingScore.setRating(thingsBean.getRatingScore());

        return convertView;
    }


    public class RankingSortViewHolder {
        @BindView(R.id.img_item_ranking_thing_cover)
        SimpleDraweeView imgItemRankingThingCover;
        @BindView(R.id.tv_item_ranking_thing_name)
        TextView tvItemRankingThingName;
        @BindView(R.id.tv_item_ranking_thing_title)
        TextView tvItemRankingThingTitle;
        @BindView(R.id.img_review)
        ImageView imgReview;
        @BindView(R.id.tv_review_btn)
        TextView tvReviewBtn;
        @BindView(R.id.ll_review)
        LinearLayout llReview;
        @BindView(R.id.rating_score)
        RatingBar ratingScore;
        @BindView(R.id.tv_score_num)
        TextView tvScoreNum;

        RankingSortViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
