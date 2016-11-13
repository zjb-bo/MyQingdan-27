package com.qingdan.myqingdan.gui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.RankingSortData;
import com.qingdan.myqingdan.gui.adapter.MyViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/13.
 */

public class RankingThingsDetailActivity extends BaseActivity {
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.goods_detail_back)
    ImageView goodsDetailBack;
    @BindView(R.id.things_listview)
    ListView thingsListview;
    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id.things_foot_comment)
    TextView thingsFootComment;
    @BindView(R.id.things_foot_share)
    TextView thingsFootShare;
    @BindView(R.id.things_foot_review)
    TextView thingsFootReview;
    private View headView;
    private HeaderViewHolder holder;

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        RankingSortData.DataBean.ThingsBean thing =
                (RankingSortData.DataBean.ThingsBean)
                        intent.getSerializableExtra("thingDetail");

        MyViewPagerAdapter mAdapter = new MyViewPagerAdapter(thing.getImageUrls(),this);
        headView = LayoutInflater.from(this).inflate(R.layout.things_header, thingsListview, false);
        holder = new HeaderViewHolder(headView);
        holder.thingHeadViewViewpager.setAdapter(mAdapter);

        holder.thingHeadViewSubtitle.setText(thing.getName());
        holder.thingHeadViewTitle.setText(thing.getFullName());
        holder.textLike.setText(thing.getLikeCount()+"");


        thingsListview.addHeaderView(headView);
        thingsListview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new ArrayList<String>()));
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

    }

    @Override
    protected int getContentViewResId() {
        return R.layout.ranking_things_detail_activity;
    }

    public class HeaderViewHolder {
        @BindView(R.id.thing_head_view_viewpager)
        ViewPager thingHeadViewViewpager;
        @BindView(R.id.dot)
        LinearLayout dot;
        @BindView(R.id.thing_head_view_subtitle)
        TextView thingHeadViewSubtitle;
        @BindView(R.id.thing_head_view_title)
        TextView thingHeadViewTitle;
        @BindView(R.id.ratingbar)
        RatingBar ratingbar;
        @BindView(R.id.good_detail)
        RelativeLayout goodDetail;
        @BindView(R.id.img)
        TextView img;
        @BindView(R.id.but_link)
        RelativeLayout butLink;
        @BindView(R.id.img_like)
        ImageView imgLike;
        @BindView(R.id.text_like)
        TextView textLike;
        @BindView(R.id.img_having)
        ImageView imgHaving;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
