package com.qingdan.myqingdan.gui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.ResponseMainListData;

/**
 * Created by Administrator on 2016/10/27.
 */

public class RecyclerCollectionsAdapter extends RecyclerBaseAdapter<ResponseMainListData.DataBean.CollectionsBean> {

    public RecyclerCollectionsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getHeaderCount() {
        return 0;
    }

    @Override
    public int getFooterCount() {
        return 1;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case HEADER_TYPE:
                //TODO 如果有头部 则作头部的处理
                break;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("RecyclerCollectionsAdap", "holder:" + holder);
        if(position < getHeaderCount()){
            //如果有头部  则处理头部控件

        }else if(position >= getItemCount()-getFooterCount()){
            //如果有尾部 处理尾部控件
            footType(holder,footType);
        }else{
            MyViewHolder mHolder = (MyViewHolder) holder;
            ResponseMainListData.DataBean.CollectionsBean nodesBean = getItem(position);
            mHolder.imageViewFrontTopImage.setImageURI(nodesBean.getFeaturedImageUrl());
            mHolder.textViewFrontMainTitle.setText(nodesBean.getTitle());
            mHolder.textViewFrontSubtitle.setText(nodesBean.getSubtitle());
            mHolder.rotateTextViewDate.setText(nodesBean.getPublishedAtDiffForHumans());
            mHolder.linearBottomCount.setVisibility(View.GONE);
        }
    }



}
