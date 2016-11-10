package com.qingdan.myqingdan.gui.mvp.model;

import com.qingdan.myqingdan.entity.ResposeBatching;

/**
 * Created by Administrator on 2016/10/20.
 */

public interface MainModelDao {
    void loadBatchingDatas(onLoadBatchingDataListener callback);
    public interface onLoadBatchingDataListener{
        void loadBatchingDataSuccess(ResposeBatching resposeBatching);
        void loadBatchingDataFailed();
    }
}
