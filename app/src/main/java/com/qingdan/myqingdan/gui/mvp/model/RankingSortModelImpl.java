package com.qingdan.myqingdan.gui.mvp.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.qingdan.myqingdan.entity.RankingSortData;
import com.qingdan.myqingdan.utils.http.HttpUtils;
import com.qingdan.myqingdan.utils.http.Request;
import com.qingdan.myqingdan.utils.http.qingdan.HttpClient;

/**
 * Created by Administrator on 2016/11/9.
 */

public class RankingSortModelImpl implements RankingSortModelDao {

    @Override
    public void loadSortData(String url, final SortDataCallBack callBack) {

        Request.Builder requset = new Request.Builder()
                .get()
                .url(url);
        HttpClient.excute(requset, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                if(TextUtils.isEmpty(response))return;

                RankingSortData data = JSON.parseObject(response,RankingSortData.class);
                if(data.getData().getMeta().getPagination().getTotal_pages() ==
                        data.getData().getMeta().getPagination().getCurrent_page()){
                    callBack.noMoreData();
                }

                callBack.loadSortDataSuccess(data);
            }

            @Override
            public void onError() {
                callBack.loadSortFailed();
            }
        });
    }
}
