package com.qingdan.myqingdan.gui.mvp.presenter;

import com.qingdan.myqingdan.entity.RankingSortData;
import com.qingdan.myqingdan.gui.mvp.model.RankingSortModelDao;
import com.qingdan.myqingdan.gui.mvp.model.RankingSortModelImpl;
import com.qingdan.myqingdan.gui.view.RankingSortDataViewDao;
import com.qingdan.myqingdan.utils.Apis;
import com.qingdan.myqingdan.utils.Contast;
import com.qingdan.myqingdan.utils.UrlHandler;

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
    public void loadSortData() {
        String url = "";
        switch (tag){
            case Contast.RANKING_SORTTAG_HOT:
                url = UrlHandler.urlHandler(Apis.URL_RANKING_HOT,id,page,10);
                break;
            case Contast.RANKING_SORTTAG_SCORE:
                url = UrlHandler.urlHandler(Apis.URL_RANKING_SCORE,id,page,10);
                break;
            case Contast.RANKING_SORTTAG_NAME:
                url = UrlHandler.urlHandler(Apis.URL_RANKING_NAME,id,page,10);
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
                page++;
            }
        });
    }
}
