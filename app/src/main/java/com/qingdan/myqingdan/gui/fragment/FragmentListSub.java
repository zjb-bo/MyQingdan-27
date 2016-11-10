package com.qingdan.myqingdan.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.ResponseMainListData;
import com.qingdan.myqingdan.entity.ResponseReputation;
import com.qingdan.myqingdan.gui.activity.ArticleDetailActivity;
import com.qingdan.myqingdan.gui.activity.CollectionDetailActivity;
import com.qingdan.myqingdan.gui.activity.RankingMoreActivity;
import com.qingdan.myqingdan.gui.activity.RankingSortActivity;
import com.qingdan.myqingdan.gui.adapter.RecyclerArticlesAdapter;
import com.qingdan.myqingdan.gui.adapter.RecyclerBaseAdapter;
import com.qingdan.myqingdan.gui.adapter.RecyclerCollectionsAdapter;
import com.qingdan.myqingdan.gui.adapter.RecyclerNodesAdapter;
import com.qingdan.myqingdan.gui.mvp.presenter.NodesPresenterDao;
import com.qingdan.myqingdan.gui.mvp.presenter.NodesPresenterImpl;
import com.qingdan.myqingdan.gui.mvp.view.NodesViewDao;
import com.qingdan.myqingdan.utils.Contast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/17.
 */

public class FragmentListSub extends BaseFragment implements NodesViewDao, RecyclerBaseAdapter.OnMyItemClick, View.OnClickListener, RecyclerNodesAdapter.OnRankingViewClick {
    @BindView(R.id.arrow_up)
    ImageView arrowUp;
    private RecyclerView rv;
    private RecyclerBaseAdapter adapter;
    private boolean isNoMoreData;
    private NodesPresenterDao dao;
    private boolean isLoading;
    private int type;
    private int footType = 3;
    private LinearLayoutManager manager;

    @Override
    protected int getContentViewResId() {
        return R.layout.sub_fragment;
    }

    /***
     * 创建带有标签的Fragment的静态方法
     *
     * @param url
     * @param type
     * @return
     */
    public static FragmentListSub creatFragmentInstance(String url, int type) {
        FragmentListSub fragment = new FragmentListSub();
        Bundle budle = new Bundle();
        budle.putString("url", url);
        budle.putInt("type", type);
        fragment.setArguments(budle);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        String url = arguments.getString("url");
        type = arguments.getInt("type");
        init();
        Log.d("FragmentListSub", url + type);
        dao = (NodesPresenterDao) new NodesPresenterImpl(this, url);
        switch (type) {
            case Contast.ARTICLES_TYPE:
                adapter = new RecyclerArticlesAdapter(getActivity());
                break;
            case Contast.CONCTIONS_TYPE:
                adapter = new RecyclerCollectionsAdapter(getActivity());
                break;
            case Contast.NODES_TYPE:
                adapter = new RecyclerNodesAdapter(getActivity());
                //设置口碑的监听为当前类监听
                ((RecyclerNodesAdapter) adapter).setOnRankingViewClick(this);
                dao.loadReputation();
                break;
        }

        rv.setAdapter(adapter);
        adapter.setOnItemClick(this);

    }

    private void init() {
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
//        manager.setOrientation(GridLayout.);
        rv = (RecyclerView) getView().findViewById(R.id.recyclerview);
        rv.setLayoutManager(manager);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isNoMoreData) {
                    return;
                }
                if (!isLoading && manager.findLastVisibleItemPosition() == manager.getItemCount() - 1) {
                    isLoading = true;
                    footType = 3;
                    dao.loadData();
                }

                if(manager.findFirstVisibleItemPosition() == 0){
                    arrowUp.setVisibility(View.GONE);
                }else if(dy < 0 && arrowUp.getVisibility() == View.GONE){
                    arrowUp.setVisibility(View.VISIBLE);
                }else if(dy > 0 && arrowUp.getVisibility() == View.VISIBLE){
                    arrowUp.setVisibility(View.GONE);
                }
            }
        });
        arrowUp.setOnClickListener(this);

    }

    @Override
    public void loadNodesSuccess(List<ResponseMainListData.DataBean.NodesBean> nodesBean) {
        isLoading = false;
        adapter.addDatas(nodesBean, footType);

    }

    @Override
    public void loadCollectionsSuccess(List<ResponseMainListData.DataBean.CollectionsBean> collectionsBeen) {
        isLoading = false;
        adapter.addDatas(collectionsBeen, footType);

    }

    @Override
    public void loadArticlesSuccess(List<ResponseMainListData.DataBean.ArticlesBean> articlesBeen) {
        isLoading = false;
        adapter.addDatas(articlesBeen, footType);

    }

    @Override
    public void noMoreData() {
        isNoMoreData = true;
        footType = 1;
    }

    @Override
    public void loadFailed() {
        footType = 2;
        adapter.addDatas(new ArrayList(), footType);
        Toast.makeText(getActivity(), "onEorror", Toast.LENGTH_SHORT).show();
    }


    /***
     * 加载口碑
     **/
    @Override
    public void loadputationData(List<ResponseReputation.DataBean.RankingsBean> rankings) {
        RecyclerNodesAdapter nodesAdapter = (RecyclerNodesAdapter) adapter;
        ((RecyclerNodesAdapter) adapter).addReputation(rankings);
    }


    @Override
    public void loadAgain() {
        isLoading = true;
        footType = 3;
        adapter.addDatas(new ArrayList(), footType);

        dao.loadData();

    }

    @Override
    public void contentItemClick(int position, View view, Object obj) {
        switch (type) {
            case Contast.ARTICLES_TYPE:
                ResponseMainListData.DataBean.ArticlesBean articles = (ResponseMainListData.DataBean.ArticlesBean) obj;
                Intent intentArticles = new Intent(getActivity(), ArticleDetailActivity.class);
                intentArticles.putExtra("articleId", articles.getId());
                startActivity(intentArticles);
                break;
            case Contast.CONCTIONS_TYPE:
                ResponseMainListData.DataBean.CollectionsBean collections = (ResponseMainListData.DataBean.CollectionsBean) obj;
                Intent intentCollection = new Intent(getActivity(), CollectionDetailActivity.class);
                intentCollection.putExtra("collectionId", collections.get_id());
                startActivity(intentCollection);
                break;
            case Contast.NODES_TYPE:
                /***最新里面有两种数据 1- 专题，2- 文章;
                 * 所以不同类型 跳转到 不同页面**/
                TextView textTag = (TextView) view.findViewById(R.id.rela_special_tag);
                ResponseMainListData.DataBean.NodesBean nodes = (ResponseMainListData.DataBean.NodesBean) obj;

                if ("专题".equals(textTag.getText())) {
                    Intent intent = new Intent(getActivity(), CollectionDetailActivity.class);
                    intent.putExtra("collectionId", nodes.getTarget_id());
                    startActivity(intent);
                } else {
                    Intent intentNodes = new Intent(getActivity(), ArticleDetailActivity.class);
                    intentNodes.putExtra("articleId", nodes.getTarget_id());
                    startActivity(intentNodes);
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**这是按钮回滚到顶部的监听事件**/
    @Override
    public void onClick(View v) {
        rv.smoothScrollToPosition(0);
    }


    /*这是口碑中的几个view的监听*/
    @Override
    public void rankingViewOnClick(int positon, View view, Object object) {
        ResponseReputation.DataBean.RankingsBean rankings =
                (ResponseReputation.DataBean.RankingsBean) object;
        Intent intent = new Intent(getActivity(),RankingSortActivity.class);
        intent.putExtra("rankingId",rankings.getId());
        intent.putExtra("rankingName",rankings.getTitle());
        startActivity(intent);
    }

    /*这是口碑中 查看更多 的监听*/
    @Override
    public void rankingMoreImageViewOnClick() {
        startActivity(new Intent(getActivity(),RankingMoreActivity.class));
    }
}
