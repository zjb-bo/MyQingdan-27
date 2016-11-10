package com.qingdan.myqingdan.gui.mvp.view;

import com.qingdan.myqingdan.entity.ResponseArticleComments;
import com.qingdan.myqingdan.entity.ResponseArticleTitle;
import com.qingdan.myqingdan.entity.ResponseRelatedArticles;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public interface ArticlDetailViewDao {
    void loadArticleTitleSuccess(ResponseArticleTitle articleTitle);
    void loadArticleDetailSuccess(String url);
    void loadCommentsSuccess(ResponseArticleComments responseArticleComments);
    void loadRelatedArticlesSuccess(List<ResponseRelatedArticles.DataBean.ArticlesBean> relatedArticles);
    void RelatedArticlesEorror();
    void loadArticleTitleEorror();
    void loadCommentEorror();
    void showLookUpGoods();
    void showLikedCount(int likedCount);
    void showCommentsCount(int commentsCount);
    void showNoComments();

    void showMoreCommentsView();
}
