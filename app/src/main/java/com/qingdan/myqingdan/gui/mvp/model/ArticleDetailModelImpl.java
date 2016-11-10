package com.qingdan.myqingdan.gui.mvp.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.qingdan.myqingdan.entity.ResponseArticleComments;
import com.qingdan.myqingdan.entity.ResponseArticleTitle;
import com.qingdan.myqingdan.entity.ResponseRelatedArticles;
import com.qingdan.myqingdan.utils.Apis;
import com.qingdan.myqingdan.utils.UrlHandler;
import com.qingdan.myqingdan.utils.http.HttpUtils;
import com.qingdan.myqingdan.utils.http.Request;
import com.qingdan.myqingdan.utils.http.qingdan.HttpClient;

/**
 * Created by Administrator on 2016/11/3.
 */

public class ArticleDetailModelImpl implements ArticleDetailModelDao {
    private Callback callback;
    private int articleId;
    @Override
    public void loadDatas(int articleId, Callback callback) {
        this.callback = callback;
        this.articleId = articleId;
        loadArticleTitle();
        loadArticleDetail();
        loadComments();
        loadRelatedArticles();
    }

    @Override
    public void loadArticleTitle() {
        String url = UrlHandler.urlHandler(Apis.URL_ARTICLE_TITLE,articleId);
        Log.d("ArticleDetailModelImpl", url);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                ResponseArticleTitle responseArticleTitle
                        = JSON.parseObject(response, ResponseArticleTitle.class);
                callback.loadArticleTitleSuccess(responseArticleTitle);
            }
            @Override
            public void onError() {
                callback.loadArticleTitleEorror();
            }
        });
    }

    @Override
    public void loadArticleDetail() {
        callback.loadArticleDetailSuccess
                (UrlHandler.urlHandler(Apis.URL_ARTICLE_DETAIL,articleId));
    }

    @Override
    public void loadComments() {
        String url = UrlHandler.urlHandler(Apis.URL_ARTICLE_COMMENTS,articleId);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                ResponseArticleComments responseArticleComments
                        = JSON.parseObject(response, ResponseArticleComments.class);
                callback.loadCommentsSuccess(responseArticleComments);

            }
            @Override
            public void onError() {
                callback.loadCommentEorror();
            }
        });
    }

    @Override
    public void loadRelatedArticles() {
        String url = UrlHandler.urlHandler(Apis.URL_RELATED_ARTICLES,articleId);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                ResponseRelatedArticles responseRelatedArticles
                        = JSON.parseObject(response, ResponseRelatedArticles.class);
                try {
                    callback.loadRelatedArticlesSuccess(responseRelatedArticles.getData().getArticles());
                }catch (Exception e){

                }
            }
            @Override
            public void onError() {
                callback.RelatedArticlesEorror();
            }
        });
    }
}
