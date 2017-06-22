package com.wyw.diyviewdemo.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jungly.gridpasswordview.GridPasswordView;
import com.wyw.diyviewdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：DIYView
 * 类描述：支付宝，微信密码支付框
 * 创建人：伍跃武
 * 创建时间：2017/6/22 10:34
 */
public class AliPayPWDActivity extends AppCompatActivity {
    @BindView(R.id.pswView)
    GridPasswordView pswView;


    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        ButterKnife.bind(this);
        context = this;
        pswView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
            }

            @Override
            public void onInputFinish(String psw) {
                Toast.makeText(context,"输入完了，。",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
