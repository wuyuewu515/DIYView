package com.wyw.diyviewdemo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 项目名称：DIYView
 * 类描述： 层叠卡片Fragment适配器
 * 创建人：伍跃武
 * 创建时间：2017/6/19 14:56
 */
public class GuidFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private String[] titles;

    public GuidFragmentAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm);
        this.mFragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles != null && titles.length > 0 ? titles[position % titles.length] : "";
    }
}
