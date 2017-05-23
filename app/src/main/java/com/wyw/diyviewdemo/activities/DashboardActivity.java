package com.wyw.diyviewdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wyw.diyviewdemo.R;
import com.wyw.diyviewdemo.views.DashBoardView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名称：DIYView
 * 类描述：仪表盘自定义图
 * 创建人：伍跃武
 * 创建时间：2017/5/23 10:41
 */
public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.boardView)
    DashBoardView boardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
        boardView.setmCreditLevel("very good");
        Random random = new Random();
        int point = random.nextInt(500) + 350;
        boardView.setmValue(point);
        boardView.setmDescript("这是描述语言");
        boardView.needIsStroke(random.nextBoolean());
    }

}
