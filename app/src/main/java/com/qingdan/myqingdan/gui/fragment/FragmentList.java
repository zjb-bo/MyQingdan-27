package com.qingdan.myqingdan.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.ResposeBatching;
import com.qingdan.myqingdan.gui.activity.ArticleDetailActivity;
import com.qingdan.myqingdan.gui.adapter.MainBottomPagerAdapter;
import com.qingdan.myqingdan.gui.adapter.MainSlidePagerAdapter;
import com.qingdan.myqingdan.gui.mvp.presenter.MainPresenterDao;
import com.qingdan.myqingdan.gui.mvp.presenter.MainPresenterImpl;
import com.qingdan.myqingdan.gui.mvp.view.MainViewDao;
import com.qingdan.myqingdan.gui.view.MyMaterialRefushLayout;
import com.qingdan.myqingdan.gui.widget.PagerDotIndctor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/17.
 */

public class FragmentList extends BaseFragment implements MainViewDao, AppBarLayout.OnOffsetChangedListener {
    @BindView(R.id.textView_title)
    TextView textViewTitle;
    @BindView(R.id.coor)
    CollapsingToolbarLayout coor;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.toor_bar)
    Toolbar toorBar;
    //    @BindView(R.id.fragment_list_refresh)
//    MaterialRefreshLayout fragmentListRefresh;
    private ViewPager viewPager, viewPagerBottom;
    private MainBottomPagerAdapter adapterBottom;
    private MainSlidePagerAdapter adapter;
    private TabLayout mtablayout;
    private LinearLayout linearLayout;
    private PagerDotIndctor pagerDotIndctor;
    private MainPresenterDao mainPresenterDao;
    private MyMaterialRefushLayout refreshLayout;

    @Override
    protected int getContentViewResId() {
        return R.layout.list_fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshLayout = (MyMaterialRefushLayout) getView().findViewById(R.id.refresh_layout);
        viewPager = (ViewPager) getView().findViewById(R.id.viewpager1);
        mtablayout = (TabLayout) getView().findViewById(R.id.tablayout);
        viewPagerBottom = (ViewPager) getView().findViewById(R.id.viewpager_bottom);
        linearLayout = (LinearLayout) getView().findViewById(R.id.dot_ll);
        pagerDotIndctor = new PagerDotIndctor(getActivity(), linearLayout, viewPager);
        //每次进入这个fragment的时候，自动刷新一次
//        refreshLayout.autoRefresh();

        mainPresenterDao = new MainPresenterImpl(this);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mainPresenterDao.loadBatchingData();
            }
        });

        mainPresenterDao.loadBatchingData();
        appBar.addOnOffsetChangedListener(this);
        //一次性 将所有子fragment的加载出来， 如果采用不移除的方法，就会下拉空白
        viewPagerBottom.setOffscreenPageLimit(14);

    }

    @Override
    public void showBatchingData(ResposeBatching resposeBatching) {
        adapter = new MainSlidePagerAdapter(getActivity(),
                resposeBatching.getData().getSlides().getBody().getData().getSlides());
        adapter.setOnItemListener( new MainSlidePagerAdapter.OnItemListener() {
            @Override
            public void itemClick(int position, View view, Object object) {
                ResposeBatching.DataBean1.SlidesBean1.BodyBean.DataBean.SlidesBean respose =
                        (ResposeBatching.DataBean1.SlidesBean1.BodyBean.DataBean.SlidesBean) object;
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                intent.putExtra("articleId", Integer.parseInt(respose.getTarget()));
                startActivity(intent);
            }
        });

        viewPager.setAdapter(adapter);

        List<ResposeBatching.DataBean1.ChannelsBean1.BodyBean.DataBean.ChannelsBean> channels =
                resposeBatching.getData().getChannels().getBody().getData().getChannels();
        adapterBottom = new MainBottomPagerAdapter(channels, getFragmentManager());


        viewPagerBottom.removeAllViews();
        viewPagerBottom.setAdapter(adapterBottom);
        mtablayout.setupWithViewPager(viewPagerBottom);

        pagerDotIndctor.setDot(resposeBatching.getData().getSlides().getBody().getData().getSlides().size());
        refreshLayout.finishRefresh();

        Toast.makeText(getActivity(), resposeBatching.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showBatchingFailed() {
        Toast.makeText(getActivity(), "onError", Toast.LENGTH_SHORT).show();
        refreshLayout.finishRefresh();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private int[] location = new int[2];

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(verticalOffset == 0){
            refreshLayout.setEablePull(true);
        }else{
            refreshLayout.setEablePull(false);
        }


        tablayout.getLocationOnScreen(location);
        if (location[1] < 2*toorBar.getHeight()){
            textViewTitle.setVisibility(View.VISIBLE);
//            textViewTitle.setAlpha((2*toorBar.getHeight() - location[1])/toorBar.getHeight());
        }else{
            textViewTitle.setVisibility(View.GONE);
        }
    }
}

