package com.qingdan.myqingdan.gui.mvp.presenter;

import com.qingdan.myqingdan.entity.ResponseArticleComments;
import com.qingdan.myqingdan.entity.ResponseArticleTitle;
import com.qingdan.myqingdan.entity.ResponseRelatedArticles;
import com.qingdan.myqingdan.gui.mvp.model.ArticleDetailModelDao;
import com.qingdan.myqingdan.gui.mvp.model.ArticleDetailModelImpl;
import com.qingdan.myqingdan.gui.mvp.view.ArticlDetailViewDao;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class ArticlePresenterImpl implements ArticlePresenterDao {
    private ArticlDetailViewDao viewDao;
    private ArticleDetailModelDao modelDao;

    public ArticlePresenterImpl(ArticlDetailViewDao viewDao) {
        this.viewDao = viewDao;
        modelDao = new ArticleDetailModelImpl();
    }

    @Override
    public void loadArticleData(int articleId) {
        modelDao.loadDatas(articleId, new ArticleDetailModelDao.Callback() {
            @Override
            public void loadArticleTitleSuccess(ResponseArticleTitle articleTitle) {
                //收到Model的回调之后，再通过View显示界面
                viewDao.loadArticleTitleSuccess(articleTitle);

                //通知View显示总评论个数
                viewDao.showCommentsCount(articleTitle.getData().getCommentCount());
                //如果评论>4条 就显示 “加载更多评论”
                if(articleTitle.getData().getCommentCount() > 4){
                    viewDao.showMoreCommentsView();
                }
                //通知View显示总喜欢个数
                viewDao.showLikedCount(articleTitle.getData().getLikeCount());

                if(articleTitle.getData().getThingCount() > 0 ){
                    viewDao.showLookUpGoods();
                }
            }

            @Override
            public void loadArticleDetailSuccess(String url) {
                viewDao.loadArticleDetailSuccess(url);
            }

            @Override
            public void loadCommentsSuccess(ResponseArticleComments responseArticleComments) {
                if(responseArticleComments.getData().getComments() == null || responseArticleComments.getData().getComments().size() == 0){
                    viewDao.showNoComments();
                }else{
                    viewDao.loadCommentsSuccess(responseArticleComments);
                }
            }

            @Override
            public void loadRelatedArticlesSuccess(List<ResponseRelatedArticles.DataBean.ArticlesBean> relatedArticles) {
                viewDao.loadRelatedArticlesSuccess(relatedArticles);
            }

            @Override
            public void RelatedArticlesEorror() {
                viewDao.RelatedArticlesEorror();
            }

            @Override
            public void loadArticleTitleEorror() {
                viewDao.loadArticleTitleEorror();
            }

            @Override
            public void loadCommentEorror() {
                viewDao.loadCommentEorror();
            }
        });
    }
}
