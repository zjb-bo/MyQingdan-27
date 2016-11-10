package com.qingdan.myqingdan.gui.mvp.view;

import com.qingdan.myqingdan.entity.ResponseMainListData;
import com.qingdan.myqingdan.entity.ResponseReputation;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public interface NodesViewDao {
     void loadNodesSuccess(List<ResponseMainListData.DataBean.NodesBean> nodesBean);
     void loadCollectionsSuccess(List<ResponseMainListData.DataBean.CollectionsBean> collectionsBeen);
     void loadArticlesSuccess(List<ResponseMainListData.DataBean.ArticlesBean> articlesBeen);
     void noMoreData();
     void loadFailed();

     void loadputationData(List<ResponseReputation.DataBean.RankingsBean> rankings);
}
