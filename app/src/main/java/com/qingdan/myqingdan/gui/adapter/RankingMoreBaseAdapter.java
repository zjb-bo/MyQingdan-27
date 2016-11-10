package com.qingdan.myqingdan.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.RankingMoreData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/8.
 */

public class RankingMoreBaseAdapter extends BaseAdapter {
    private List<RankingMoreData.DataBean.RankingsBean> list;
    private LayoutInflater inflater;

    public RankingMoreBaseAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }

    public void addData(List<RankingMoreData.DataBean.RankingsBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
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
        RankingViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ranking_more_item, null);
            holder = new RankingViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (RankingViewHolder) convertView.getTag();
        }

        holder.rangkMoreCount.setText("点评数 "+list.get(position).getReviewCount()+"");
        holder.rangkMoreThing.setText("商品数 "+list.get(position).getThingCount()+"");
        holder.rangkMoreTitle.setText(list.get(position).getTitle());
        holder.rankingMoreImg.setImageURI(list.get(position).getFeaturedImageUrl());

        return convertView;
    }

    static class RankingViewHolder {
        @BindView(R.id.ranking_more_img)
        SimpleDraweeView rankingMoreImg;
        @BindView(R.id.rangk_more_title)
        TextView rangkMoreTitle;
        @BindView(R.id.rangk_more_thing)
        TextView rangkMoreThing;
        @BindView(R.id.rangk_more_count)
        TextView rangkMoreCount;

        RankingViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
