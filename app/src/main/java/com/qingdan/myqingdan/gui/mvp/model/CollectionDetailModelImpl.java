package com.qingdan.myqingdan.gui.mvp.model;

import com.alibaba.fastjson.JSON;
import com.qingdan.myqingdan.entity.ResponseCollectionDetail;
import com.qingdan.myqingdan.utils.Apis;
import com.qingdan.myqingdan.utils.http.FormBody;
import com.qingdan.myqingdan.utils.http.HttpUtils;
import com.qingdan.myqingdan.utils.http.Request;
import com.qingdan.myqingdan.utils.http.RequestBody;
import com.qingdan.myqingdan.utils.http.qingdan.HttpClient;

/**
 * Created by Administrator on 2016/11/4.
 */

public class CollectionDetailModelImpl implements CollectionDetailModelDao {
    @Override
    public void loadCollectionDetail(final CollectionDetailCallback callback, int collectionId) {
        String url = Apis.URL_COLLECTION;

        RequestBody body = new FormBody.Builder()
                .add("requests","{\"collections\":" +
                        "{\"method\":\"GET\",\"relative_url\":\"/v1/collections/"+collectionId+"/articles\"}," +
                        "\"collection\":{\"method\":\"GET\",\"relative_url\":\"/v1/collections/"+collectionId+"\"}}")
                .build();

        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body);

        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
//                Log.d("aa", UnicodeParser.decodeUnicode(response));
                if(response == null || response.equals(""))return;
                ResponseCollectionDetail collection = JSON.parseObject(response,ResponseCollectionDetail.class);
                callback.collectionDetailDataSuccess(collection);
            }
            @Override
            public void onError() {
                callback.collectionDetailFailed();
            }
        });
    }
}
