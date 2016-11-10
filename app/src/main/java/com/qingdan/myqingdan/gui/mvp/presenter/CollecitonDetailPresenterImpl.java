package com.qingdan.myqingdan.gui.mvp.presenter;

import com.qingdan.myqingdan.entity.ResponseCollectionDetail;
import com.qingdan.myqingdan.gui.mvp.model.CollectionDetailModelDao;
import com.qingdan.myqingdan.gui.mvp.model.CollectionDetailModelImpl;
import com.qingdan.myqingdan.gui.mvp.view.CollectionDetailViewDao;

/**
 * Created by Administrator on 2016/11/4.
 */

public class CollecitonDetailPresenterImpl implements CollectionDetailPresenterDao {
    private CollectionDetailViewDao view;
    private CollectionDetailModelDao model;

    public CollecitonDetailPresenterImpl(CollectionDetailViewDao view) {
        this.view = view;
        model = new CollectionDetailModelImpl();
    }

    @Override
    public void loadCollectionDetailData(int collection) {
        model.loadCollectionDetail(new CollectionDetailModelDao.CollectionDetailCallback() {
            @Override
            public void collectionDetailDataSuccess(ResponseCollectionDetail collection) {
                view.showCollectionDetailSuccess(collection);
            }

            @Override
            public void collectionDetailFailed() {
                view.showCollectionDetailFailed();
            }
        },collection);
    }
}
