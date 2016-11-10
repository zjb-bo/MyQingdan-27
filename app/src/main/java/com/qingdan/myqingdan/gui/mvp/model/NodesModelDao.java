package com.qingdan.myqingdan.gui.mvp.model;

import com.qingdan.myqingdan.entity.ResponseMainListData;
import com.qingdan.myqingdan.entity.ResponseReputation;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public interface NodesModelDao {
    void loadData(NodesCallBack callBack,String url);

    public interface NodesCallBack {
        void loadNodesSuccess(List<ResponseMainListData.DataBean.NodesBean> nodesBean);
        void loadArticlesSuccess(List<ResponseMainListData.DataBean.ArticlesBean> articles);
        void loadCollectionsSuccess(List<ResponseMainListData.DataBean.CollectionsBean> collectionsBeen);
        void noMoreData();
        void loadFailed();
    }

    void loadReputationData(ReputationCallback callback);
    public interface ReputationCallback{
        void loadSuccess(List<ResponseReputation.DataBean.RankingsBean> rankings);
        void loadFailed();
    }
}