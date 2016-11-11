package com.qingdan.myqingdan.gui.mvp.presenter;

import android.text.TextUtils;

import com.qingdan.myqingdan.entity.RankingSortData;
import com.qingdan.myqingdan.gui.mvp.model.RankingSortModelDao;
import com.qingdan.myqingdan.gui.mvp.model.RankingSortModelImpl;
import com.qingdan.myqingdan.gui.view.RankingSortDataViewDao;
import com.qingdan.myqingdan.utils.Apis;
import com.qingdan.myqingdan.utils.Contast;
import com.qingdan.myqingdan.utils.UrlHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/11/9.
 */

public class RankingSortPresentImpl implements RankingSortDataPresenterDao {
    private RankingSortModelDao model;
    private RankingSortDataViewDao view;
    private int tag;
    private int id;
    private int page = 1;

    public RankingSortPresentImpl(int tag,int id,RankingSortDataViewDao view) {
        this.view = view;
        this.id = id;
        this.tag = tag;
        this.model = new RankingSortModelImpl();
    }

    @Override
    public void refreshData(String key) {
        page = 1;
        loadSortData(key);
    }

    @Override
    public void loadSortData(String key) {
        String url = "";
        if (!TextUtils.isEmpty(key)){
            try {
                key = URLEncoder.encode(key,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            key = "";
        }

        switch (tag){
            case Contast.RANKING_SORTTAG_HOT:
                url = UrlHandler.urlHandler(Apis.URL_RANKING_HOT,id,key,page);
                break;
            case Contast.RANKING_SORTTAG_SCORE:
                url = UrlHandler.urlHandler(Apis.URL_RANKING_SCORE,id,key,page);
                break;
            case Contast.RANKING_SORTTAG_NAME:
                url = UrlHandler.urlHandler(Apis.URL_RANKING_NAME,id,key,page);
                break;
        }

        model.loadSortData(url, new RankingSortModelDao.SortDataCallBack() {
            @Override
            public void loadSortFailed() {
                view.showRankingFailed();
            }

            @Override
            public void noMoreData() {
                view.showNoMoreData();
            }

            @Override
            public void loadSortDataSuccess(RankingSortData data) {
                view.showRankingSuccuse(data);
                if(data.getData().getThings().size() == 0)return;
                page++;
            }
        });
    }
}
