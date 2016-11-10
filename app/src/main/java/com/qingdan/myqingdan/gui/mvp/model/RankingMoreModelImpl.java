package com.qingdan.myqingdan.gui.mvp.model;

import com.alibaba.fastjson.JSON;
import com.qingdan.myqingdan.entity.RankingMoreData;
import com.qingdan.myqingdan.utils.http.HttpUtils;
import com.qingdan.myqingdan.utils.http.Request;
import com.qingdan.myqingdan.utils.http.qingdan.HttpClient;

/**
 * Created by Administrator on 2016/11/8.
 */

public class RankingMoreModelImpl implements RankingMoreModelDao {
    @Override
    public void loadRankingMoreData(final RankingMoreCallBack callBack, String url) {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                if(response.isEmpty())return;

                RankingMoreData rankings = JSON.parseObject(response,RankingMoreData.class);

                if(rankings.getData().getMeta().getPagination().getCurrent_page() ==
                        rankings.getData().getMeta().getPagination().getTotal_pages()){
                    callBack.RankingNoMoreData();
                    return;
                }


                callBack.loadRankingMoreDataSuccess(rankings);



            }

            @Override
            public void onError() {
                callBack.loadRankingMoreDataFailed();
            }
        });
    }
}
