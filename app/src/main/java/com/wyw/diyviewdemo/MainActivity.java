package com.wyw.diyviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.meituan.android.walle.WalleChannelReader;
import com.wyw.diyviewdemo.activities.AliPayPWDActivity;
import com.wyw.diyviewdemo.activities.CardViewActivity;
import com.wyw.diyviewdemo.activities.DashboardActivity;
import com.wyw.diyviewdemo.activities.RecyclDragRecyActivity;
import com.wyw.diyviewdemo.activities.TanTanActivity;
import com.wyw.diyviewdemo.activities.WaterWaveActivity;
import com.wyw.diyviewdemo.activities.WaveBallActivity;
import com.wyw.diyviewdemo.interfaces.RecyclerDragListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String channel = WalleChannelReader.getChannel(this);
        Toast.makeText(this, channel, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_dashbord, R.id.btn_dashbord2, R.id.btn_dashbord3,
            R.id.btn_viewCard, R.id.btn_tanTan, R.id.btn_recy, R.id.btn_ali})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_dashbord: { //仪表盘视图
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btn_dashbord2: { //水纹球进度条效果
                Intent intent = new Intent(this, WaveBallActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btn_dashbord3: { //水纹效果图
                Intent intent = new Intent(this, WaterWaveActivity.class);
                startActivity(intent);

            }
            break;
            case R.id.btn_viewCard: { //层叠卡片
                Intent intent = new Intent(this, CardViewActivity.class);
                startActivity(intent);

            }
            break;
            case R.id.btn_tanTan: { //层叠卡片，仿探探效果
                Intent intent = new Intent(this, TanTanActivity.class);
                startActivity(intent);

            }
            break;
            case R.id.btn_recy: { //可拖拽的recycleView
                Intent intent = new Intent(this, RecyclDragRecyActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btn_ali: { //支付宝，微信支付框
                Intent intent = new Intent(this, AliPayPWDActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}


