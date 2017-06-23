package com.wyw.diyviewdemo.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jungly.gridpasswordview.GridPasswordView;
import com.wyw.diyviewdemo.R;
import com.wyw.diyviewdemo.views.PayPwdEditText;

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

   @BindView(R.id.payEdit)
    PayPwdEditText payEdit;


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
                Toast.makeText(context, "输入完了，。", Toast.LENGTH_SHORT).show();

            }
        });

        payEdit.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.colorPrimary, R.color.colorPrimary, 20);
        payEdit.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                Toast.makeText(AliPayPWDActivity.this, "显示明文：" + str, Toast.LENGTH_SHORT).show();
                payEdit.setShowPwd(false);
            }
        });
    }
}
