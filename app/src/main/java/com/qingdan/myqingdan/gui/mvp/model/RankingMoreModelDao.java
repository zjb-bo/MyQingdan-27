package com.qingdan.myqingdan.gui.mvp.model;

import com.qingdan.myqingdan.entity.RankingMoreData;

/**
 * Created by Administrator on 2016/11/8.
 */

public interface RankingMoreModelDao {
    void loadRankingMoreData(RankingMoreCallBack callBack,String url);
    public interface RankingMoreCallBack{
        void loadRankingMoreDataSuccess(RankingMoreData rankings);
        void loadRankingMoreDataFailed();
        void RankingNoMoreData();
    }

}
