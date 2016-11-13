package com.qingdan.myqingdan.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.RankingSortData;
import com.qingdan.myqingdan.gui.activity.RankingThingsDetailActivity;
import com.qingdan.myqingdan.gui.adapter.RankingSortBaseAdapter;
import com.qingdan.myqingdan.gui.mvp.presenter.RankingSortPresentImpl;
import com.qingdan.myqingdan.gui.view.MyMaterialRefushLayout;
import com.qingdan.myqingdan.gui.view.RankingSortDataViewDao;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/9.
 */

public class FragmentRankingSort extends BaseFragment implements RankingSortDataViewDao, AdapterView.OnItemClickListener {
    @BindView(R.id.rankingfragment_listview)
    ListView rankingfragmentListview;
    @BindView(R.id.rankingfragment_reflush)
    MyMaterialRefushLayout rankingfragmentReflush;
    private RankingSortPresentImpl mPresent;
    private RankingSortBaseAdapter mAdapter;
    private String key = "";
    private boolean isLoading = true;
    private boolean isNoMoreData;
    private View view;


    @Override
    protected int getContentViewResId() {
        return R.layout.ranking_sort_fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public static FragmentRankingSort newInstence(int tag, int rankingId) {
        FragmentRankingSort fragment = new FragmentRankingSort();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        bundle.putInt("rankingId", rankingId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setSearchKey(String key) {
        this.key = key;
    }

    public void showFragment(String key) {
        if (this.key.equals(key)) {
            return;
        }
        mAdapter.clearData();
        mPresent.refreshData(key);
        this.key = key;
        isNoMoreData = false;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();
        int tag = arguments.getInt("tag", 0);
        int rankingId = arguments.getInt("rankingId", 0);

        mPresent = new RankingSortPresentImpl(tag, rankingId, this);
        mPresent.loadSortData(key);

        view = LayoutInflater.from(getActivity()).inflate(R.layout.ranking_sort_footerview, rankingfragmentListview,false);
        rankingfragmentListview.addFooterView(view);

        mAdapter = new RankingSortBaseAdapter(getActivity());
        rankingfragmentListview.setAdapter(mAdapter);

        rankingfragmentListview.setOnItemClickListener(this);

        rankingfragmentListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isNoMoreData) {
                    return;
                }
                Log.d("FragmentRankingSort", "visibleItemCount:" + visibleItemCount+"  "+totalItemCount+" "+firstVisibleItem);


                if (!isLoading && visibleItemCount == totalItemCount - firstVisibleItem) {
                    mPresent.loadSortData(key);
                    isLoading = true;
                }
            }
        });

        rankingfragmentReflush.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mPresent.loadSortData(key);
            }
        });


    }

    @Override
    public void showRankingSuccuse(RankingSortData data) {
        isLoading = false;
//        view.findViewById(R.id.linear_other).setVisibility(View.GONE);
//        view.findViewById(R.id.text_failed).setVisibility(View.GONE);
//        view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        rankingfragmentReflush.finishRefresh();
        mAdapter.addData(data.getData().getThings());
    }

    @Override
    public void showRankingFailed() {
//        view.findViewById(R.id.linear_other).setVisibility(View.GONE);
//        view.findViewById(R.id.text_failed).setVisibility(View.VISIBLE);
//        view.findViewById(R.id.progressBar).setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
        rankingfragmentReflush.finishRefresh();
        Toast.makeText(getActivity(), "OnEorror", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoMoreData() {
//        view.findViewById(R.id.linear_other).setVisibility(View.VISIBLE);
//        view.findViewById(R.id.text_failed).setVisibility(View.GONE);
//        view.findViewById(R.id.progressBar).setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
        isNoMoreData = true;
        rankingfragmentReflush.finishRefresh();
        Toast.makeText(getActivity(), "NoMoreData", Toast.LENGTH_SHORT).show();
    }


    /***
     * 跳转到每一个商品的详细信息
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), RankingThingsDetailActivity.class);
        RankingSortData.DataBean.ThingsBean thingsBean =
                (RankingSortData.DataBean.ThingsBean) mAdapter.getItem(position);
        intent.putExtra("thingDetail",thingsBean);
        startActivity(intent);
    }
}
