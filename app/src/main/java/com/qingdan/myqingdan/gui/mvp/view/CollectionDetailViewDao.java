package com.qingdan.myqingdan.gui.mvp.view;

import com.qingdan.myqingdan.entity.ResponseCollectionDetail;

/**
 * Created by Administrator on 2016/11/4.
 */

public interface CollectionDetailViewDao {
    void showCollectionDetailSuccess(ResponseCollectionDetail collection);
    void showCollectionDetailFailed();
}
