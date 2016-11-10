package com.qingdan.myqingdan.gui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.qingdan.myqingdan.R;

/**
 * Created by Administrator on 2016/10/17.
 */

public class GruidActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(GruidActivity.this,MainActivity.class));
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
        return R.layout.gruid_activity;
    }


}
