package com.qingdan.myqingdan.gui.mvp.model;

import com.qingdan.myqingdan.entity.RankingSortData;

/**
 * Created by Administrator on 2016/11/9.
 */

public interface RankingSortModelDao {
    void loadSortData(String url,SortDataCallBack callBack);
    public interface SortDataCallBack{

        void loadSortFailed();
        void noMoreData();

        void loadSortDataSuccess(RankingSortData data);
    }
}
