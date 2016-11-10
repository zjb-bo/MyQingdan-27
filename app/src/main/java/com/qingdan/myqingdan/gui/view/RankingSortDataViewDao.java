package com.qingdan.myqingdan.gui.view;

import com.qingdan.myqingdan.entity.RankingSortData;

/**
 * Created by Administrator on 2016/11/9.
 */

public interface RankingSortDataViewDao {

    void showRankingSuccuse(RankingSortData data);
    void showRankingFailed();
    void showNoMoreData();
}
