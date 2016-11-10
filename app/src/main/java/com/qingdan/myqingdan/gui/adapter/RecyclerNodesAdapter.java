package com.qingdan.myqingdan.gui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.ResponseMainListData;
import com.qingdan.myqingdan.entity.ResponseReputation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.qingdan.myqingdan.R.id.img_ranking_all_topic_enter;

/**
 * Created by Administrator on 2016/10/27.
 */

public class RecyclerNodesAdapter extends RecyclerBaseAdapter<ResponseMainListData.DataBean.NodesBean> {
    private List<ResponseReputation.DataBean.RankingsBean> list;
    public RecyclerNodesAdapter(Context context) {
        super(context);
        list = new ArrayList<>();
    }

    @Override
    public int getHeaderCount() {
        return 1;
    }

    @Override
    public int getFooterCount() {
        return 1;
    }

    public void addReputation(List<ResponseReputation.DataBean.RankingsBean> rankings){
//        list.clear();
        list.addAll(rankings);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case HEADER_TYPE:
                View view = inflater.inflate(R.layout.layout_head_enter_rankings, parent, false);
                return new HeaderViewHolder(view);
            case FOOTER_TYPE:
                //TODO 如果有尾部 则作尾部的处理
                View vFooter = inflater.inflate(R.layout.footer_item, parent, false);
                return new FooterViewHolder(vFooter);
            case CONTENT_TYPE:
                //TODO 做内容item的处理
                View vContent = inflater.inflate(R.layout.main_list_item, parent, false);
                return new MyViewHolder(vContent);
        }
        return new MyViewHolder(inflater.inflate(R.layout.main_list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < getHeaderCount()) {
            //如果有头部  则处理头部控件
            HeaderViewHolder mHeaderHolder = (HeaderViewHolder) holder;
            if(mHeaderHolder.llItemRankingEnter.getChildCount() == 0){
                for (int i = 0; i <list.size(); i++) {
                    final int index = i;
                    final View view = inflater.inflate(R.layout.subview_reputation,mHeaderHolder.llItemRankingEnter,false);
                    SimpleDraweeView viewById = (SimpleDraweeView) view.findViewById(img_ranking_all_topic_enter);
                    viewById.setImageURI(list.get(i).getThumbnailImageUrl());
                    mHeaderHolder.llItemRankingEnter.addView(view);
                    //对动态添加的口碑View进行设置监听和回调
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(rangkingListener != null){
                                rangkingListener.rankingViewOnClick(position,view,list.get(index));
                            }
                        }
                    });

                }
            }
            //为什么不把设置图片的操作放在上面的if里面？
            //因为if中只是执行一次，放在上面是因为有可能第一次图片没有加载成功，
            //后面都不会加载了，图片不能正确的显示
            //放在下面每一次滑动都加载，所以是为了防止图片不能正常加载
//            for (int i = 0; i < simpleDraweeViews.size(); i++) {
//                simpleDraweeViews.get(i).setImageURI(rankings.get(i).getThumbnailImageUrl());
//            }

        } else if (position >= getItemCount() - getFooterCount()) {
            //如果有尾部 处理尾部控件
            footType(holder, footType);

        } else {
            MyViewHolder mHolder = (MyViewHolder) holder;
            ResponseMainListData.DataBean.NodesBean nodesBean = getItem(position);
            mHolder.imageViewFrontTopImage.setImageURI(nodesBean.getFeaturedImageUrl());
            mHolder.textViewFrontMainTitle.setText(nodesBean.getTitle());
            mHolder.textViewFrontSubtitle.setText(nodesBean.getSubtitle());
            mHolder.textViewFrontNumLiked.setText(nodesBean.getLikeCount() + "");
            mHolder.textViewNumReviews.setText(nodesBean.getHitCount() + "");
            mHolder.rotateTextViewDate.setText(nodesBean.getPublishedAtDiffForHumans());
            if(nodesBean.getCategories() != null && nodesBean.getCategories().size() > 0){
                mHolder.relaSpecialTag.setText(nodesBean.getCategories().get(0).getName());
            }

        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ll_item_ranking_enter)
        LinearLayout llItemRankingEnter;
        @BindView(R.id.img_ranking_all_topic_enter)
        ImageView moreImage;
        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            //回调查看更多被点击的情况
            moreImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rangkingListener != null){
                        rangkingListener.rankingMoreImageViewOnClick();
                    }
                }
            });
        }
    }

    /*对口碑里面动态添加的几个view进行点击监听*/
    public interface OnRankingViewClick{
        void rankingViewOnClick(int positon,View view,Object object);
        void rankingMoreImageViewOnClick();

    }

    private OnRankingViewClick rangkingListener;
    public void setOnRankingViewClick(OnRankingViewClick rangkingListener){
        this.rangkingListener = rangkingListener;
    }
}
