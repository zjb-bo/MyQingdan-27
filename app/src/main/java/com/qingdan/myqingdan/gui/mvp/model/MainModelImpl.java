package com.qingdan.myqingdan.gui.mvp.model;

import com.alibaba.fastjson.JSON;
import com.qingdan.myqingdan.entity.ResposeBatching;
import com.qingdan.myqingdan.utils.http.FormBody;
import com.qingdan.myqingdan.utils.http.HttpUtils;
import com.qingdan.myqingdan.utils.http.Request;
import com.qingdan.myqingdan.utils.http.RequestBody;
import com.qingdan.myqingdan.utils.http.qingdan.HttpClient;

/**
 * Created by Administrator on 2016/10/20.
 */

public class MainModelImpl implements MainModelDao {



    @Override
    public void loadBatchingDatas(final onLoadBatchingDataListener callback) {
        RequestBody body = new FormBody.Builder()
                .add("requests","{\"channels\":{\"method\":\"GET\",\"relative_url\":\"/v1/channels\"},\"slides\":{\"method\":\"GET\",\"relative_url\":\"/v1/slides2\"}}")
                .build();
        Request.Builder builder = new Request.Builder()
                .addHeader("qd-manufacturer", "Xiaomi")
                .addHeader("qd-model", "MI 5")
                .addHeader("qd-os", "Android")
                .addHeader("qd-screen-height", "1920")
                .addHeader("qd-carrier", "%E4%B8%AD%E5%9B%BD%E8%81%94%E9%80%9A")
                .addHeader("qd-network-type", "WIFI")
                .addHeader("qd-app-id", "com.eqingdan")
                .addHeader("qd-app-version", "2.6")
                .addHeader("qd-app-channel", "mi")
                .addHeader("qd-track-device-id", "eb51c9b1f01ac05c32170fc4cf18d0e7")
                .addHeader("User-Agent", "EQingDan/2.5 (Android; okhttp/2.4.0)")
                .post(body)
                .url("http://api.eqingdan.com/v1/batching");
        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                ResposeBatching resposeBatching = JSON.parseObject(response,ResposeBatching.class);
                callback.loadBatchingDataSuccess(resposeBatching);
            }

            @Override
            public void onError() {
                callback.loadBatchingDataFailed();
            }
        });
    }
}
