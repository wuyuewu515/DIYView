package com.wyw.diyviewdemo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wyw.diyviewdemo.R;
import com.wyw.diyviewdemo.adapters.GuidFragmentAdapter;
import com.wyw.diyviewdemo.fragments.GuideFragment;
import com.wyw.diyviewdemo.helpers.CardPageTransformer;
import com.wyw.diyviewdemo.helpers.PageTransformerConfig;
import com.wyw.diyviewdemo.interfaces.OnPageTransformerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：DIYView
 * 类描述： 层叠卡片视图
 * 创建人：伍跃武
 * 创建时间：2017/6/19 14:05
 */
public class CardViewActivity extends AppCompatActivity {
    private Activity mActivity;
    private ViewPager vpMain;
    private GuidFragmentAdapter guidFragmentAdapter;
    private List<Fragment> fragments;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);
        fragments = getFragments();

        vpMain.setOffscreenPageLimit(fragments.size() * 2);

        initPager(PageTransformerConfig.BOTTOM);
    }

    private void initPager(@PageTransformerConfig.ViewType int mViewType) {

        vpMain.setPageTransformer(true, CardPageTransformer.getBuild()//建造者模式
        .addAnimationType(PageTransformerConfig.ROTATION) //默认动画效果
                .setRotation(-50)
                .setAlpha(PageTransformerConfig.ALPHA)
                .setViewType(mViewType)
                .setOnPageTransformerListener(new OnPageTransformerListener() {
                    @Override
                    public void onPageTransformerListener(View page, float position) {
                        //你也可以在这里对 page 实行自定义动画 cust anim
                    }
                })
                .setTranslationOffset(40)
                .setScaleOffset(80)
                .create());

        //创建适配器
        guidFragmentAdapter = new GuidFragmentAdapter(getSupportFragmentManager(), fragments, null);
        vpMain.setAdapter(guidFragmentAdapter);


    }

    /**
     * 创建模拟数据
     *
     * @return Fragment的集合
     */
    private List<Fragment> getFragments() {
        vpMain = (ViewPager) findViewById(R.id.vp_main);
        List<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mFragments.add(GuideFragment.newInstance(i));
        }
        return mFragments;
    }
}
