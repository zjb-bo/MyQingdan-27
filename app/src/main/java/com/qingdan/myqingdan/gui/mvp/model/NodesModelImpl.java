package com.qingdan.myqingdan.gui.mvp.model;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.qingdan.myqingdan.entity.ResponseMainListData;
import com.qingdan.myqingdan.entity.ResponseReputation;
import com.qingdan.myqingdan.utils.Apis;
import com.qingdan.myqingdan.utils.http.HttpUtils;
import com.qingdan.myqingdan.utils.http.Request;
import com.qingdan.myqingdan.utils.http.qingdan.HttpClient;

/**
 * Created by Administrator on 2016/10/24.
 */

public class NodesModelImpl implements NodesModelDao {
    @Override
    public void loadData(final NodesCallBack callback,String url) {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                Log.d("NodesModelImpl", response);
                ResponseMainListData responsedata = JSON.parseObject(response, ResponseMainListData.class);
                if(TextUtils.isEmpty(response))return;

                //如果当前页面是最后一页，则执行noMoredata
                if (responsedata.getData().getMeta().getPagination().getTotal_pages() ==
                        responsedata.getData().getMeta().getPagination().getCurrent_page()){
                    callback.noMoreData();
                }



                if(responsedata.getData().getNodes() != null){
                    //TODO 回调nodes数据
                    callback.loadNodesSuccess(responsedata.getData().getNodes());
                }else if(responsedata.getData().getArticles() != null){
                    //TODO 回到articles 方法
                    callback.loadArticlesSuccess(responsedata.getData().getArticles());
                }else if(responsedata.getData().getCollections() != null){
                    //TODO 回调Collecitons
                    callback.loadCollectionsSuccess(responsedata.getData().getCollections());
                }
            }

            @Override
            public void onError() {
                callback.loadFailed();
            }
        });

    }

    @Override
    public void loadReputationData(final ReputationCallback callback) {
        Request.Builder builder = new Request.Builder()
                .url(Apis.URL_REPUTATION)
                .get();
        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                ResponseReputation responseReputation = JSON.parseObject(response, ResponseReputation.class);
                if(responseReputation.getCode() == 0){//code=0 代表访问成功
                    callback.loadSuccess(responseReputation.getData().getRankings());
                }
            }

            @Override
            public void onError() {

            }
        });
    }

}
