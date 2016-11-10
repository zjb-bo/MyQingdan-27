package com.qingdan.myqingdan.gui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.RankingSortData;
import com.qingdan.myqingdan.gui.adapter.RankingSortBaseAdapter;
import com.qingdan.myqingdan.gui.mvp.presenter.RankingSortPresentImpl;
import com.qingdan.myqingdan.gui.view.MyMaterialRefushLayout;
import com.qingdan.myqingdan.gui.view.RankingSortDataViewDao;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/9.
 */

public class FragmentRankingSort extends BaseFragment implements RankingSortDataViewDao {
    @BindView(R.id.rankingfragment_listview)
    ListView rankingfragmentListview;
    @BindView(R.id.rankingfragment_reflush)
    MyMaterialRefushLayout rankingfragmentReflush;
    private RankingSortPresentImpl mPresent;
    private RankingSortBaseAdapter mAdapter;

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

    public static FragmentRankingSort newInstence(int tag,int rankingId){
        FragmentRankingSort fragment = new FragmentRankingSort();
        Bundle bundle = new Bundle();
        bundle.putInt("tag",tag);
        bundle.putInt("rankingId",rankingId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();
        int tag = arguments.getInt("tag",0);
        int rankingId = arguments.getInt("rankingId",0);

        mPresent = new RankingSortPresentImpl(tag,rankingId,this);
        mPresent.loadSortData();

        mAdapter = new RankingSortBaseAdapter(getActivity());
        rankingfragmentListview.setAdapter(mAdapter);

        rankingfragmentReflush.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mPresent.loadSortData();
            }
        });
    }

    @Override
    public void showRankingSuccuse(RankingSortData data) {
        rankingfragmentReflush.finishRefresh();
        mAdapter.addData(data.getData().getThings());
    }

    @Override
    public void showRankingFailed() {
        rankingfragmentReflush.finishRefresh();
        Toast.makeText(getActivity(), "OnEorror", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoMoreData() {
        rankingfragmentReflush.finishRefresh();
        Toast.makeText(getActivity(), "NoMoreData", Toast.LENGTH_SHORT).show();

    }
}
