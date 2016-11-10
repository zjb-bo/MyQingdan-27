package com.qingdan.myqingdan.gui.mvp.presenter;

import com.qingdan.myqingdan.entity.ResponseMainListData;
import com.qingdan.myqingdan.entity.ResponseReputation;
import com.qingdan.myqingdan.gui.mvp.model.NodesModelDao;
import com.qingdan.myqingdan.gui.mvp.model.NodesModelImpl;
import com.qingdan.myqingdan.gui.mvp.view.NodesViewDao;
import com.qingdan.myqingdan.utils.UrlHandler;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class NodesPresenterImpl implements NodesPresenterDao{

    private NodesViewDao viewDao;
    private NodesModelDao nodesModelDao;
    private String url;
    private int nextpage = 1;

    public NodesPresenterImpl(NodesViewDao nodesViewDao,String url){
        this.viewDao = nodesViewDao;
        this.url = url;
        nodesModelDao = new NodesModelImpl();
    }



    @Override
    public void loadData() {

        String urlTag = UrlHandler.urlHandler(url,nextpage);
        nodesModelDao.loadData(new NodesModelDao.NodesCallBack() {
            @Override
            public void loadNodesSuccess(List<ResponseMainListData.DataBean.NodesBean> nodesBean) {
                viewDao.loadNodesSuccess(nodesBean);

            }

            @Override
            public void loadCollectionsSuccess(List<ResponseMainListData.DataBean.CollectionsBean> collectionsBeen) {
                viewDao.loadCollectionsSuccess(collectionsBeen);

            }

            @Override
            public void loadArticlesSuccess(List<ResponseMainListData.DataBean.ArticlesBean> articlesBeen) {
                viewDao.loadArticlesSuccess(articlesBeen);
            }

            @Override
            public void noMoreData() {
                viewDao.noMoreData();
            }

            @Override
            public void loadFailed() {
                viewDao.loadFailed();
                nextpage--;
            }
        },urlTag);
        nextpage++;
    }

    @Override
    public void loadReputation() {
        nodesModelDao.loadReputationData(new NodesModelDao.ReputationCallback() {
            @Override
            public void loadSuccess(List<ResponseReputation.DataBean.RankingsBean> rankings) {
                viewDao.loadputationData(rankings);
            }

            @Override
            public void loadFailed() {
                viewDao.loadFailed();
            }
        });
    }
}
