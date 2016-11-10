package com.qingdan.myqingdan.gui.mvp.view;

import com.qingdan.myqingdan.entity.ResposeBatching;

/**
 * Created by Administrator on 2016/10/20.
 */

public interface MainViewDao {
    void showBatchingData(ResposeBatching resposeBatching);
    void showBatchingFailed();

}
