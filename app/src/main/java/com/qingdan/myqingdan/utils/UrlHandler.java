package com.qingdan.myqingdan.utils;

/**
 * Created by Administrator on 2016/10/27.
 */

/***
 * http://cnblogs.davismy.com/Handler.ashx?op=GetTimeLine&channelpath={0}&page={1}
 * {"data/123","1"}

 */

public class UrlHandler {

    public static String  urlHandler(String url,Object... pragms){
        for (int i = 0; i < pragms.length; i++) {
            url = url.replace("{"+i+"}",pragms[i]+"");
        }
        return url;
    }
}
