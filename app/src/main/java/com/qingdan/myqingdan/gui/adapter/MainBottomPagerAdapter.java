package com.qingdan.myqingdan.gui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.qingdan.myqingdan.entity.ResposeBatching;
import com.qingdan.myqingdan.gui.fragment.FragmentListSub;
import com.qingdan.myqingdan.utils.Contast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */

public class MainBottomPagerAdapter extends FragmentPagerAdapter{
    private List<ResposeBatching.DataBean1.ChannelsBean1.BodyBean.DataBean.ChannelsBean> datas;
    private List<Fragment> fragments;


    public MainBottomPagerAdapter(List<ResposeBatching.DataBean1.ChannelsBean1.BodyBean.DataBean.ChannelsBean> datas,
                                  FragmentManager fm) {
        super(fm);
        this.datas = datas;
        fragments = new ArrayList<>();
        /*这是 最新 的url和标签*/
        fragments.add(FragmentListSub.creatFragmentInstance("http://api.eqingdan.com/v1/front?page={0}",Contast.NODES_TYPE));
        for (int i = 0; i < datas.size(); i++) {
            if("collections".equals(datas.get(i).getType())){
                fragments.add(FragmentListSub.creatFragmentInstance("http://api.eqingdan.com/v1/collections?page={0}",Contast.CONCTIONS_TYPE));
            }else if("articles_belong_to_category".equals(datas.get(i).getType())){
                fragments.add(FragmentListSub.creatFragmentInstance(
                        "http://api.eqingdan.com/v1/categories/"+
                                datas.get(i).getExtra().getCategory_slug()+
                                "/articles?page={0}",Contast.ARTICLES_TYPE));
            }else if ("articles_belong_to_collection".equals(datas.get(i).getType())){
                fragments.add(FragmentListSub.creatFragmentInstance(
                        "http://api.eqingdan.com/v1/collections/"
                                + datas.get(i).getExtra().getCollection_id()
                                +"/articles?page={0}",Contast.ARTICLES_TYPE));
            }
        }

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container,position,object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "最新";
        }
        return datas.get(position-1).getTitle();
    }
}
