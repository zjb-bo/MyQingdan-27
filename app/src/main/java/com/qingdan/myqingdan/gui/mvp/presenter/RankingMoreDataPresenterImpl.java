package com.qingdan.myqingdan.gui.mvp.presenter;

import com.qingdan.myqingdan.entity.RankingMoreData;
import com.qingdan.myqingdan.gui.mvp.model.RankingMoreModelDao;
import com.qingdan.myqingdan.gui.mvp.model.RankingMoreModelImpl;
import com.qingdan.myqingdan.gui.mvp.view.RankingMoreViewDao;

/**
 * Created by Administrator on 2016/11/8.
 */

public class RankingMoreDataPresenterImpl implements RankingMoreDataPresenterDao {
    private RankingMoreModelDao moreModel;
    private RankingMoreViewDao viewDao;
    public int page=1;
    public RankingMoreDataPresenterImpl(RankingMoreViewDao viewDao) {
        this.viewDao = viewDao;
        this.moreModel = new RankingMoreModelImpl();
    }

    @Override
    public void loadRankingMoreData() {
        String url = "http://api.eqingdan.com/v1/rankings?page="+page+"&per=10";

        moreModel.loadRankingMoreData(new RankingMoreModelDao.RankingMoreCallBack() {
            @Override
            public void loadRankingMoreDataSuccess(RankingMoreData rankings) {
                viewDao.showRankingMoreDataSuccess(rankings);
                page++;
            }

            @Override
            public void loadRankingMoreDataFailed() {
                viewDao.showRankingMoreDataFailed();
            }

            @Override
            public void RankingNoMoreData() {
                viewDao.RankingNoMoreData();
            }
        },url);
    }
}
