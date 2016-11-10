package com.qingdan.myqingdan.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qingdan.myqingdan.R;
import com.qingdan.myqingdan.gui.fragment.FragmentRankingSort;
import com.qingdan.myqingdan.utils.Contast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/9.
 */

public class RankingSortActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.goods_detail_back)
    ImageView goodsDetailBack;
    @BindView(R.id.ranking_sort_search)
    ImageView rankingSortSearch;
    @BindView(R.id.ranking_sort_edit)
    EditText rankingSortEdit;
    @BindView(R.id.ranking_sort_delete)
    ImageView rankingSortDelete;
    @BindView(R.id.ranking_sort_tag)
    RadioGroup rankingSortTag;
    @BindView(R.id.ranking_sort_frame)
    FrameLayout rankingSortFrame;
    @BindView(R.id.rangking_sort_cancle)
    TextView rangkingSortCancle;
    @BindView(R.id.ranking_sort_hot)
    RadioButton rankingSortHot;
    @BindView(R.id.ranking_sort_score)
    RadioButton rankingSortScore;
    @BindView(R.id.ranking_sort_name)
    RadioButton rankingSortName;
    private List<FragmentRankingSort> list;
    private FragmentManager mManager;

    @Override
    protected void initDatas() {
        mManager = getSupportFragmentManager();
        list = new ArrayList<>();
        Intent intent = getIntent();
        int id = intent.getIntExtra("rankingId", 0);
        String name = intent.getStringExtra("rankingName");
        textTitle.setText(name);

        list.add(FragmentRankingSort.newInstence(Contast.RANKING_SORTTAG_HOT, id));
        list.add(FragmentRankingSort.newInstence(Contast.RANKING_SORTTAG_SCORE, id));
        list.add(FragmentRankingSort.newInstence(Contast.RANKING_SORTTAG_NAME, id));

        rankingSortTag.setOnCheckedChangeListener(this);
        showFragment(0);

        rankingSortEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    rankingSortDelete.setVisibility(View.GONE);
                }else{
                    rankingSortDelete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.ranking_sort_activity;
    }

    @OnClick({R.id.text_title, R.id.goods_detail_back, R.id.ranking_sort_search, R.id.ranking_sort_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goods_detail_back:
                finish();
                break;
            case R.id.ranking_sort_search:
                break;
            case R.id.ranking_sort_delete:
                rankingSortEdit.getText().clear();
                rankingSortDelete.setVisibility(View.GONE);
                break;
            case R.id.rangking_sort_cancle:
                rangkingSortCancle.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * RaidoGroup的子类选中监听
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.ranking_sort_hot:
                showFragment(0);
                break;
            case R.id.ranking_sort_score:
                showFragment(1);
                break;
            case R.id.ranking_sort_name:
                showFragment(2);
                break;

        }
    }



    private FragmentRankingSort currentFragment;

    /***
     * 显示选中的fragment
     * @param index
     */
    private void showFragment(int index) {
        FragmentTransaction transaction = mManager.beginTransaction();
        FragmentRankingSort fragment = list.get(index);
        if (currentFragment == fragment ){
            return;
        }
        if(fragment.isAdded()){
            transaction.show(fragment);
        }else{
            transaction.add(R.id.ranking_sort_frame,fragment);
        }

        if(currentFragment != null){
            transaction.hide(currentFragment);
        }
        transaction.commit();
        currentFragment = fragment;

    }

}
