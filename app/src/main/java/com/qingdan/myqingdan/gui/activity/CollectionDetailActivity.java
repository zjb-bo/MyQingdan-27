package com.qingdan.myqingdan.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.ResponseCollectionDetail;
import com.qingdan.myqingdan.gui.adapter.CollectionBaseAdapter;
import com.qingdan.myqingdan.gui.mvp.presenter.CollecitonDetailPresenterImpl;
import com.qingdan.myqingdan.gui.mvp.view.CollectionDetailViewDao;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/4.
 */

public class CollectionDetailActivity extends BaseActivity implements CollectionDetailViewDao, AdapterView.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.collections_detail)
    ListView collectionsDetail;
    @BindView(R.id.collections_detail_back)
    ImageView collectionsDetailBack;
    @BindView(R.id.list_refresh_layout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id._collection_arrow_up)
    ImageView arrowUp;
//    @BindView(R.id.collection_detail_root)
//    LinearLayout collectionRoot;

    private CollecitonDetailPresenterImpl presenter;
    private CollectionBaseAdapter adapter;
    private CollectionViewHolder holder;
    private int currentVisbilityItem;

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        final int collectionId = intent.getIntExtra("collectionId", 0);

        collectionsDetailBack.setOnClickListener(this);
        presenter.loadCollectionDetailData(collectionId);

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                presenter.loadCollectionDetailData(collectionId);
            }
        });

        /***点击滚到顶部***/
        arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionsDetail.smoothScrollToPosition(0);
            }
        });



        collectionsDetail.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                if(firstVisibleItem == 0){
                    arrowUp.setVisibility(View.GONE);
                }else if(firstVisibleItem > 0 && currentVisbilityItem > firstVisibleItem){
                    arrowUp.setVisibility(View.VISIBLE);
                }
                currentVisbilityItem = firstVisibleItem;
            }
        });
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        View headerView = LayoutInflater.from(this).inflate(R.layout.collection_details_header, null);
        holder = new CollectionViewHolder(headerView);
        presenter = new CollecitonDetailPresenterImpl(this);
        adapter = new CollectionBaseAdapter(this);

        //添加头部
        collectionsDetail.addHeaderView(headerView);
        collectionsDetail.setAdapter(adapter);
        collectionsDetail.setOnItemClickListener(this);

        //判断19版本以上才设置pading  设置状态栏的
//        if (Build.VERSION.SDK_INT >= 19)
//            collectionRoot.setPadding(0, getStatusHeight(), 0, 0);
    }
    @Override
    protected void initBeforeSetView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    @Override
    protected int getContentViewResId() {
        return R.layout.collectonsdetailactivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }

    @Override
    public void showCollectionDetailSuccess(ResponseCollectionDetail collection) {
        refreshLayout.finishRefresh();
        adapter.addData(collection, holder);
    }

    @Override
    public void showCollectionDetailFailed() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //因为listView 加了头部，所以内容 这里的下标应该-1
        //return 表示不处理头部点击事件
        if (position > 0) {
            position -= 1;
        } else {
            return;
        }
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        ResponseCollectionDetail.DataBean1.CollectionsBean.BodyBean.DataBean.ArticlesBean data =
                (ResponseCollectionDetail.DataBean1.CollectionsBean.BodyBean.DataBean.ArticlesBean)
                        adapter.getItem(position);
        intent.putExtra("articleId", data.getId());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    public class CollectionViewHolder {
        @BindView(R.id.collection_detail_img)
        public SimpleDraweeView collectionDetailImg;
        @BindView(R.id.collection_detail_title)
        public TextView collectionDetailTitle;
        @BindView(R.id.collection_detail_subtitle)
        public TextView collectionDetailSubtitle;
        @BindView(R.id.collection_detail_tag)
        public TextView collectionDetailTag;

        CollectionViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
