package com.wyw.diyviewdemo.activities;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;

import com.wyw.diyviewdemo.R;
import com.wyw.diyviewdemo.views.WaterWaveView;
import com.wyw.diyviewdemo.views.WaveBallView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：DIYView
 * 类描述：水纹特效
 * 创建人：伍跃武
 * 创建时间：2017/5/23 10:41
 * 参考博客:http://blog.csdn.net/ta893115871/article/details/52245815
 */
public class WaterWaveActivity extends AppCompatActivity {
    @BindView(R.id.waveBall)
    WaterWaveView waterWaveView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterwave);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
    }

}
