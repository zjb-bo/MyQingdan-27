package com.qingdan.myqingdan;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Administrator on 2016/10/20.
 */

public class QingdanApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //脸书框架初始化
        Fresco.initialize(this);
    }
}
