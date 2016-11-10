package com.qingdan.myqingdan.gui.mvp.view;

import com.qingdan.myqingdan.entity.RankingMoreData;

/**
 * Created by Administrator on 2016/11/8.
 */

public interface RankingMoreViewDao {

    void showRankingMoreDataSuccess(RankingMoreData rankings);
    void showRankingMoreDataFailed();
    void RankingNoMoreData();
}
