package com.qingdan.myqingdan.gui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.utils.AppVersion;

/**
 * Created by Administrator on 2016/10/17.
 */

public class WelcomActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (exitByUser)
                    return;

                if(ifFirst()){
                    goGruidActivity();
                }else{
                    goMainActivity();
                }

            }

            private void goMainActivity() {
                startActivity(new Intent(WelcomActivity.this,MainActivity.class));
                finish();
            }

            private void goGruidActivity() {
                startActivity(new Intent(WelcomActivity.this,GruidActivity.class));
                finish();
            }
        }.start();
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getContentViewResId() {
        return R.layout.welcom_activity;
    }

    public boolean ifFirst(){
        SharedPreferences sp = getSharedPreferences("App_version",MODE_PRIVATE);
        String version = sp.getString("version", "");
        Log.i("msg", "version: "+version);
        Log.i("msg", "AppVersion: "+AppVersion.getAppName(this));

        if(version.equals(AppVersion.getAppName(this))){
            return false;
        }else {
            sp.edit().putString("version",AppVersion.getAppName(this)).commit();
            return true;
        }
    }

    private boolean exitByUser;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exitByUser = true;
    }
}
