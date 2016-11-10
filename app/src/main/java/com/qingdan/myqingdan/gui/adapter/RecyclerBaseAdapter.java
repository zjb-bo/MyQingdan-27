package com.qingdan.myqingdan.gui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qingdan.myqingdan.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/27.
 */

public abstract class RecyclerBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int HEADER_TYPE = 2;
    final int CONTENT_TYPE = 3;
    final int FOOTER_TYPE = 4;

    private List<T> list;
    protected int footType;
    public LayoutInflater inflater;

    public RecyclerBaseAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        list = new ArrayList<T>();
    }
    /***
     * 获得view的类型，是头部还是底部还是内容 好加载相应的布局视图
     *
     * @param position
     * @return// 0 12345   6   5
     */
    @Override
    public int getItemViewType(int position) {
        if (position < getHeaderCount())
            return HEADER_TYPE;
        else if (position == getItemCount() - getFooterCount())
            return FOOTER_TYPE;
        else
            return CONTENT_TYPE;
    }
    public void addDatas(List<T> data,int footType) {
        list.addAll(data);
        notifyDataSetChanged();
        this.footType = footType;
    }

    /*获得头部的数量*/
    public abstract int getHeaderCount();
    /*获得底部的数量*/
    public abstract int getFooterCount();



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.main_list_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public int getItemCount() {
        /**考虑了头部和底部**/
        return list.size()+getHeaderCount()+getFooterCount();
    }

   public T getItem(int posion) {
       /**考虑了头部和底部**/
        T t = list.get(posion-getHeaderCount());
       return t;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView_front_top_image)
        SimpleDraweeView imageViewFrontTopImage;
        @BindView(R.id.view_temp)
        View viewTemp;
        @BindView(R.id.textView_front_main_title)
        TextView textViewFrontMainTitle;
        @BindView(R.id.textView_front_subtitle)
        TextView textViewFrontSubtitle;
        @BindView(R.id.imageView_front_like)
        ImageView imageViewFrontLike;
        @BindView(R.id.textView_front_num_liked)
        TextView textViewFrontNumLiked;
        @BindView(R.id.textView_num_reviews)
        TextView textViewNumReviews;
        @BindView(R.id.linear_bottom_count)
        LinearLayout linearBottomCount;
        @BindView(R.id.linear_temp)
        LinearLayout linearTemp;
        @BindView(R.id.rotate_textView_date)
        TextView rotateTextViewDate;
        @BindView(R.id.rela_temp)
        RelativeLayout relaTemp;
        @BindView(R.id.rela_special_tag)
        TextView relaSpecialTag;

        MyViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.contentItemClick(getAdapterPosition()-getHeaderCount(),view,
                                list.get(getAdapterPosition()-getHeaderCount()));
                    }
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.footer_item_textview)
        TextView footerItemTextview;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.loadAgain();
                    }
                }
            });
        }
    }

    private OnMyItemClick listener;
    public void setOnItemClick(OnMyItemClick listener){
        this.listener = listener;
    }
    public interface OnMyItemClick{
        void loadAgain();
        void contentItemClick(int position,View view,Object obj);
    }


    public void footType(RecyclerView.ViewHolder holder, int footType){
        FooterViewHolder mFooterHolder = (FooterViewHolder) holder;
        switch (footType){
            case 1:
                mFooterHolder.progressBar.setVisibility(View.GONE);
                mFooterHolder.footerItemTextview.setVisibility(View.VISIBLE);
                mFooterHolder.footerItemTextview.setText("没有更多数据了~");
                break;
            case 2:
                mFooterHolder.progressBar.setVisibility(View.GONE);
                mFooterHolder.footerItemTextview.setVisibility(View.VISIBLE);
                mFooterHolder.footerItemTextview.setText("加载失败，请重试...");
                break;
            case 3:
                mFooterHolder.progressBar.setVisibility(View.VISIBLE);
                mFooterHolder.footerItemTextview.setVisibility(View.GONE);
                break;
        }
    }
}
