package com.qingdan.myqingdan.gui.mvp.presenter;

import com.qingdan.myqingdan.entity.ResposeBatching;
import com.qingdan.myqingdan.gui.mvp.model.MainModelDao;
import com.qingdan.myqingdan.gui.mvp.model.MainModelImpl;
import com.qingdan.myqingdan.gui.mvp.view.MainViewDao;

/**
 * Created by Administrator on 2016/10/20.
 */

public class MainPresenterImpl implements MainPresenterDao{
    private MainModelDao mainModelDao;
    private MainViewDao mainViewDao;

    public MainPresenterImpl(MainViewDao mainViewDao) {
        mainModelDao = new MainModelImpl();
        this.mainViewDao = mainViewDao;
    }

    @Override
    public void loadBatchingData() {
        mainModelDao.loadBatchingDatas(new MainModelDao.onLoadBatchingDataListener() {
            @Override
            public void loadBatchingDataSuccess(ResposeBatching resposeBatching) {
                mainViewDao.showBatchingData(resposeBatching);
            }

            @Override
            public void loadBatchingDataFailed() {
                mainViewDao.showBatchingFailed();
            }
        });
    }
}
