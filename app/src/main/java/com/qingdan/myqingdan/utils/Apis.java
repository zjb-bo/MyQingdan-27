package com.qingdan.myqingdan.utils;

/**
 * Created by LG on 2016/10/27.
 * Tips:
 */

public interface Apis {
    /**主页 口碑对应的接口**/
    String URL_REPUTATION = "http://api.eqingdan.com/v1/rankings/front";

    /**文章详情相关接口**/

    //文章标题区域对应接口
    String URL_ARTICLE_TITLE = "http://api.eqingdan.com/v1/articles/{0}";
    //文章详情对应的webview地址
    String URL_ARTICLE_DETAIL = "http://www.eqingdan.com/app/articles/{0}";
    //评论区
    String URL_ARTICLE_COMMENTS = "http://api.eqingdan.com/v1/comments/hot?target_type=article&target_id={0}&page=1&per_page=4";
    //相关文章接口
    String URL_RELATED_ARTICLES = "http://api.eqingdan.com/v1/articles/{0}/related-articles";

    /**专题接口**/
    String URL_COLLECTION = "http://api.eqingdan.com/v1/batching";

    /***口碑相关***/

//    热门url:
    String URL_RANKING_HOT="http://api.eqingdan.com/v1/rankings/{0}/things?keyword=&sort=review-count-desc&page={1}&per={2}";
//    评分url：
    String URL_RANKING_SCORE="http://api.eqingdan.com/v1/rankings/{0}/things?keyword=&sort=rating-score-desc&page={1}&per={2}";
//    名称url：
    String URL_RANKING_NAME= "http://api.eqingdan.com/v1/rankings/{0}/things?keyword=&sort=brand-name-asc&page={1}&per={2}";
}
