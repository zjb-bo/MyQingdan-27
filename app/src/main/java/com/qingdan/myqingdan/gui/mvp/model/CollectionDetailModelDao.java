package com.qingdan.myqingdan.gui.mvp.model;

import com.qingdan.myqingdan.entity.ResponseCollectionDetail;

/**
 * Created by Administrator on 2016/11/4.
 */

public interface CollectionDetailModelDao {
    void loadCollectionDetail(CollectionDetailCallback callback,int collectionId);

    public interface CollectionDetailCallback{
        void collectionDetailDataSuccess(ResponseCollectionDetail collection);
        void collectionDetailFailed();
    }
}
