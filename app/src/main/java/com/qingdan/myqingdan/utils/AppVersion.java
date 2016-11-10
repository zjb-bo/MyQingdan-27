package com.qingdan.myqingdan.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2016/10/17.
 */

public class AppVersion {

    /***
     * 获得当前版本的是code
     * @param context
     * @return
     */
    public static int getAppcode(Context context){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = packageManager.getPackageInfo(context.getPackageName(),0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /***
     * 获得当前APP版本名
     * @param context
     * @return
     */
    public static String getAppName(Context context){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = packageManager.getPackageInfo(context.getPackageName(),0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
