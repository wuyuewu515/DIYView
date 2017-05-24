package com.wyw.diyviewdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wyw.diyviewdemo.R;
import com.wyw.diyviewdemo.views.DashBoardView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：DIYView
 * 类描述：水波球视图
 * 创建人：伍跃武
 * 创建时间：2017/5/23 10:41
 * 参考博客:http://blog.csdn.net/ta893115871/article/details/52245815
 */
public class WaveBallActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballwave);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
    }

}
