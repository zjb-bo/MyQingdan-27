package com.qingdan.myqingdan.gui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.entity.ResponseArticleComments;
import com.qingdan.myqingdan.entity.ResponseArticleTitle;
import com.qingdan.myqingdan.entity.ResponseRelatedArticles;
import com.qingdan.myqingdan.gui.adapter.MyGridViewBaseAdapter;
import com.qingdan.myqingdan.gui.mvp.presenter.ArticlePresenterDao;
import com.qingdan.myqingdan.gui.mvp.presenter.ArticlePresenterImpl;
import com.qingdan.myqingdan.gui.mvp.view.ArticlDetailViewDao;
import com.qingdan.myqingdan.gui.view.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/2.
 */

public class ArticleDetailActivity extends BaseActivity implements ArticlDetailViewDao, AdapterView.OnItemClickListener {
    @BindView(R.id.goods_detail_back)
    ImageView goodsDetailBack;
    @BindView(R.id.imageView_big_subview_article_title)
    SimpleDraweeView imageViewBigSubviewArticleTitle;
    @BindView(R.id.textView_tag_subview_article_title)
    TextView textViewTagSubviewArticleTitle;
    @BindView(R.id.textView_title_subview_article_title)
    TextView textViewTitleSubviewArticleTitle;
    @BindView(R.id.imageView_author_subview_article_title)
    SimpleDraweeView imageViewAuthorSubviewArticleTitle;
    @BindView(R.id.textView_author_nickname_subview_article_title)
    TextView textViewAuthorNicknameSubviewArticleTitle;
    @BindView(R.id.textView_publish_time_subview_article_title)
    TextView textViewPublishTimeSubviewArticleTitle;
    @BindView(R.id.textView_author_tag_subview_article_title)
    TextView textViewAuthorTagSubviewArticleTitle;
    @BindView(R.id.goods_detail_footer_like)
    LinearLayout goodsDetailFooterLike;
    @BindView(R.id.goods_detail_footer_comment)
    LinearLayout goodsDetailFooterComment;
    @BindView(R.id.goods_detail_footer_share)
    LinearLayout goodsDetailFooterShare;
    @BindView(R.id.goods_detail_footer_look)
    LinearLayout goodsDetailFooterLook;
    @BindView(R.id.good_detail_webview)
    WebView goodDetailWebview;
    @BindView(R.id.textView_write_comment)
    TextView textViewWriteComment;
    @BindView(R.id.rela_no_comments)
    RelativeLayout relaNoComments;
    @BindView(R.id.layout_comments)
    LinearLayout layoutComments;
    @BindView(R.id.layout_container_subiew_comments)
    LinearLayout layoutContainerSubiewComments;
    @BindView(R.id.goods_detail_footer_textview_like)
    TextView goodsDetailFooterTextviewLike;
    @BindView(R.id.goods_detail_footer_textview_comment)
    TextView goodsDetailFooterTextviewComment;
    @BindView(R.id.goods_detail_footer_textview_share)
    TextView goodsDetailFooterTextviewShare;
    @BindView(R.id.relactied_articl_gridView)
    MyGridView relactiedArticlGridView;
    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id.article_arrow_up)
    ImageView articleArrowUp;
    @BindView(R.id.root_article)
    RelativeLayout rootArticle;
    @BindView(R.id.article_scrollview)
    ScrollView articleScrollview;
    private ArticlePresenterDao presenter;
    private WebSettings settings;
    private MyGridViewBaseAdapter baseAdapter;

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        final int articleId = intent.getIntExtra("articleId", 0);


        relactiedArticlGridView.setNumColumns(2);
        baseAdapter = new MyGridViewBaseAdapter(this);
        relactiedArticlGridView.setAdapter(baseAdapter);
        relactiedArticlGridView.setOnItemClickListener(this);
        presenter.loadArticleData(articleId);

        articleArrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articleScrollview.smoothScrollTo(0,0);
            }
        });


//        refreshLayout.autoRefresh();
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                presenter.loadArticleData(articleId);
            }
        });
    }

    @Override
    protected void initBeforeSetView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        presenter = new ArticlePresenterImpl(this);
        settings = goodDetailWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        goodDetailWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //判断19版本以上才设置pading  设置状态栏的
        if (Build.VERSION.SDK_INT >= 19)
            rootArticle.setPadding(0, getStatusHeight(), 0, 0);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.goodsdetailactivity;
    }

    @Override
    public void loadArticleTitleSuccess(ResponseArticleTitle articleTitle) {
        refreshLayout.finishRefresh();

        imageViewBigSubviewArticleTitle.setImageURI(articleTitle.getData().getFeaturedImageUrl());
        textViewTitleSubviewArticleTitle.setText(articleTitle.getData().getTitle());
        imageViewAuthorSubviewArticleTitle.setImageURI(articleTitle.getData().getAuthor().getAvatarUrl());
        textViewAuthorNicknameSubviewArticleTitle.setText(articleTitle.getData().getAuthor().getNickname());
        textViewAuthorTagSubviewArticleTitle.setText(articleTitle.getData().getAuthor().getTagline());
        textViewPublishTimeSubviewArticleTitle.setText(articleTitle.getData().getPublishedAtDiffForHumans());
        if (articleTitle.getData().getCategories() != null && articleTitle.getData().getCategories().size() != 0) {
            textViewTagSubviewArticleTitle.setText(articleTitle.getData().getCategories().get(0).getName());
        }
    }

    @Override
    public void loadArticleDetailSuccess(String url) {
        goodDetailWebview.loadUrl(url);
    }

    @Override
    public void loadCommentsSuccess(ResponseArticleComments responseArticleComments) {
        List<ResponseArticleComments.DataBean.CommentsBean> comments =
                responseArticleComments.getData().getComments();
        //根据评论数据动态向容器中添加View并设置View数据
        LayoutInflater inflater = LayoutInflater.from(this);
        layoutComments.setVisibility(View.VISIBLE);
        layoutComments.removeAllViews();//不加这一行在刷新的时候 评论条数会一直增加
        for (int i = 0; i < comments.size(); i++) {
            View view = inflater.inflate(R.layout.list_item_comment, layoutComments, false);
            CommmentItemViewHolder holder = new CommmentItemViewHolder(view);
            ResponseArticleComments.DataBean.CommentsBean comment = comments.get(i);
            holder.imageViewAuthorAvatar.setImageURI(comment.getUser().getAvatarUrl());
            holder.textViewAuthorName.setText(comment.getUser().getNickname());
            holder.textViewCommentTimeTag.setText(comment.getCreatedAtDiffForHumans());

            if (comment.getReplyToUser() == null) {
                holder.textViewComments.setText(comment.getBody());
            } else {
                String replyUserNickName = comment.getReplyToUser().getNickname();
                holder.textViewComments.setText("回复 " + replyUserNickName + "：" + comment.getBody());
                //TODO  一个TextView显示多种颜色的文字
            }

            holder.textViewCommentLikeCount.setText(comment.getUpvoteCount() + "");
            layoutComments.addView(view);
        }

    }

    /**
     * 相关文章的设置
     *
     * @param relatedArticles
     */
    @Override
    public void loadRelatedArticlesSuccess(List<ResponseRelatedArticles.DataBean.ArticlesBean> relatedArticles) {
        baseAdapter.addData(relatedArticles);
    }

    @Override
    public void RelatedArticlesEorror() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void loadArticleTitleEorror() {

    }

    @Override
    public void loadCommentEorror() {

    }

    /***
     * 查看商品的设置
     */
    @Override
    public void showLookUpGoods() {
        goodsDetailFooterLook.setVisibility(View.VISIBLE);
    }

    /**
     * 喜欢人数
     *
     * @param likedCount
     */
    @Override
    public void showLikedCount(int likedCount) {
        goodsDetailFooterTextviewLike.setText(getString(R.string.like_count, likedCount));
    }

    /**
     * 评论显示数 最多四条
     *
     * @param commentsCount
     */
    @Override
    public void showCommentsCount(int commentsCount) {
        goodsDetailFooterTextviewComment.setText(getString(R.string.comments_count, commentsCount));
    }

    /***
     * 没有评论
     */
    @Override
    public void showNoComments() {
        relaNoComments.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMoreCommentsView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }

    @OnClick({R.id.goods_detail_back, R.id.imageView_author_subview_article_title, R.id.textView_author_nickname_subview_article_title, R.id.goods_detail_footer_like, R.id.goods_detail_footer_comment, R.id.goods_detail_footer_share, R.id.goods_detail_footer_look})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goods_detail_back:
                finish();
                break;
            case R.id.imageView_author_subview_article_title:
                break;
            case R.id.textView_author_nickname_subview_article_title:
                break;
            case R.id.goods_detail_footer_like:
                break;
            case R.id.goods_detail_footer_comment:
                break;
            case R.id.goods_detail_footer_share:
                break;
            case R.id.goods_detail_footer_look:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ResponseRelatedArticles.DataBean.ArticlesBean article =
                (ResponseRelatedArticles.DataBean.ArticlesBean) baseAdapter.getItem(position);
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra("articleId", article.getId());
        startActivity(intent);

    }

    static class CommmentItemViewHolder {
        @BindView(R.id.imageView_author_avatar)
        SimpleDraweeView imageViewAuthorAvatar;
        @BindView(R.id.textView_author_name)
        TextView textViewAuthorName;
        @BindView(R.id.textView_comment_time_tag)
        TextView textViewCommentTimeTag;
        @BindView(R.id.textView_comment_like_count)
        TextView textViewCommentLikeCount;
        @BindView(R.id.imageView_comment_like)
        ImageView imageViewCommentLike;
        @BindView(R.id.textView_comments)
        TextView textViewComments;

        CommmentItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    /***
     * 设置webview的暂停和继续
     */
    @Override
    protected void onPause() {
        super.onPause();
        goodDetailWebview.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        goodDetailWebview.onResume();
    }
}
