package com.wyw.diyviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wyw.diyviewdemo.activities.DashboardActivity;
import com.wyw.diyviewdemo.activities.WaveBallActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_dashbord, R.id.btn_dashbord2, R.id.btn_dashbord3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_dashbord: { //仪表盘视图
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btn_dashbord2: { //水纹球
                Intent intent = new Intent(this, WaveBallActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btn_dashbord3:
                break;
        }
    }
}


