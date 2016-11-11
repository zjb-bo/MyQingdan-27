package com.qingdan.myqingdan.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.RankingMoreData;
import com.qingdan.myqingdan.gui.adapter.RankingMoreBaseAdapter;
import com.qingdan.myqingdan.gui.mvp.presenter.RankingMoreDataPresenterDao;
import com.qingdan.myqingdan.gui.mvp.presenter.RankingMoreDataPresenterImpl;
import com.qingdan.myqingdan.gui.mvp.view.RankingMoreViewDao;
import com.qingdan.myqingdan.gui.view.MyMaterialRefushLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/8.
 */
public class RankingMoreActivity extends BaseActivity implements RankingMoreViewDao, AdapterView.OnItemClickListener {
    @BindView(R.id.goods_detail_back)
    ImageView goodsDetailBack;
    @BindView(R.id.ranking_more_listview)
    ListView rankingMoreListview;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.ranking_more_reflush)
    MyMaterialRefushLayout rankingMoreReflush;
    private RankingMoreBaseAdapter adapter;
    private RankingMoreDataPresenterDao presenter;
    private boolean isLoading = true;
    private boolean isNoMoreData = false;
    private List<RankingMoreData.DataBean.RankingsBean> rankings;


    @Override
    protected void initDatas() {
        rankings = new ArrayList<>();
        adapter = new RankingMoreBaseAdapter(this);
        rankingMoreListview.addHeaderView(LayoutInflater.from(this).
                inflate(R.layout.ranking_moredata_headerview,rankingMoreListview,false));
        rankingMoreListview.setAdapter(adapter);
        presenter = new RankingMoreDataPresenterImpl(this);
        presenter.loadRankingMoreData();
        rankingMoreReflush.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                presenter.loadRankingMoreData();
            }
        });

        rankingMoreListview.setOnItemClickListener(this);
        rankingMoreListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isNoMoreData) {
                    return;
                }
                if (visibleItemCount == totalItemCount - firstVisibleItem && !isLoading) {
                    presenter.loadRankingMoreData();
                    isLoading = true;
                }
            }
        });


    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        textTitle.setText(getString(R.string.ranking));
        goodsDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.ranking_more_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }

    @Override
    public void showRankingMoreDataSuccess(RankingMoreData rankings) {
        rankingMoreReflush.finishRefresh();
        isLoading = false;
        adapter.addData(rankings.getData().getRankings());

        this.rankings.addAll(rankings.getData().getRankings());
    }

    @Override
    public void showRankingMoreDataFailed() {
        rankingMoreReflush.finishRefresh();
        isLoading = false;
        Toast.makeText(this, "showRankingMoreDataFailed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RankingNoMoreData() {
        rankingMoreReflush.finishRefresh();
        isLoading = false;
        isNoMoreData = true;
        Toast.makeText(this, "RankingNoMoreData", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            return;
        }else{
            position -= 1;
        }
        Intent intent = new Intent(this,RankingSortActivity.class);
        intent.putExtra("rankingId",rankings.get(position).getId());
        intent.putExtra("rankingName",rankings.get(position).getTitle());
        startActivity(intent);
    }
}
