package com.wyw.diyviewdemo.views;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 项目名称：DIYView
 * 类描述：自定义文本编辑框
 * 创建人：伍跃武
 * 创建时间：2017/6/22 14:05
 */
public class PayPwdEditText extends RelativeLayout {
    private EditText editText;//文本编辑框
    private LinearLayout linearLayout; //文本密码的文本

    private TextView[] textViews;//文本数据
    private int pwdLength = 6;  //默认密码的长度为6

    private Context context;
    private OnTextFinishListener onTextFinishListener;

    public PayPwdEditText(Context context) {
        this(context, null);
    }

    public PayPwdEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayPwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * @param bgDrawable   背景
     * @param pwdLength    密码长度
     * @param spilinewidth 分割线宽度
     * @param spilineColor 分割线颜色
     * @param pwdColor     密码颜色
     * @param pwdSize      密码字体大小
     */
    public void initStyle(int bgDrawable, int pwdLength, float spilinewidth
            , int spilineColor, int pwdColor, int pwdSize) {
        this.pwdLength = pwdLength;
        initEdit(bgDrawable);
        initShowInput(bgDrawable, pwdLength, spilinewidth, spilineColor, pwdColor, pwdSize);
    }

    /**
     * 展示密码输入框
     *
     * @param bgDrawable   背景色
     * @param pwdLength    密码的长度
     * @param spilinewidth 分割线的宽度
     * @param spilineColor 分割线的颜色
     * @param pwdColor     密码的颜色
     * @param pwdSize      密码的大小
     */
    private void initShowInput(int bgDrawable, int pwdLength,
                               float spilinewidth, int spilineColor, int pwdColor, int pwdSize) {
        //添加密码框父容器
        linearLayout = new LinearLayout(context);
        linearLayout.setBackgroundResource(bgDrawable);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(linearLayout);

        //添加密码框
        textViews = new TextView[pwdLength];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(dip2px(context, spilinewidth), LayoutParams.MATCH_PARENT);
        for (int i = 0; i < textViews.length; i++) {
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textViews[i] = textView;
            textViews[i].setTextSize(pwdSize);
            textViews[i].setTextColor(context.getResources().getColor(pwdColor));
            textViews[i].setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
            linearLayout.addView(textView, params);

            if (i < textViews.length - 1) {
                View view = new View(context);
                view.setBackgroundColor(context.getResources().getColor(spilineColor));
                linearLayout.addView(view, params1);
            }
        }

    }

    /**
     * 初始化编辑框
     *
     * @param bgDrawable 编辑框背景颜色
     */
    private void initEdit(int bgDrawable) {
        editText = new EditText(context);
        editText.setBackgroundResource(bgDrawable);
        editText.setCursorVisible(false); //是否有光标
        editText.setTextSize(0);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(pwdLength)});
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Editable etext = editText.getText();
                Selection.setSelection(etext, etext.length());//将光标定位在最后的位置上
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                initDatas(s);
                if (s.length() == pwdLength) { //密码输入完毕
                    if (onTextFinishListener != null) {
                        onTextFinishListener.onFinish(s.toString().trim());
                    }
                }
            }
        });
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        addView(editText, lp);

    }

    /**
     * 根据字符，显示密码的个数
     *
     * @param s
     */
    private void initDatas(Editable s) {
        int length = s.length();
        if (length > 0) {
            for (int i = 0; i < pwdLength; i++) {
                if (i < length) {
                    for (int j = 0; j < length; j++) {
                        char ch = s.charAt(j);
                        textViews[j].setText(String.valueOf(ch));
                    }
                } else {
                    textViews[i].setText("");
                }
            }
        } else {
            for (int i = 0; i < pwdLength; i++) {
                textViews[i].setText("");
            }
        }
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * 是否显示明文
     *
     * @param showPwd
     */
    public void setShowPwd(boolean showPwd) {
        int length = textViews.length;
        for (int i = 0; i < length; i++) {
            if (showPwd) {
                textViews[i].setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                textViews[i].setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        }
    }

    /**
     * 设置显示类型
     *
     * @param type
     */
    public void setInputType(int type) {
        int length = textViews.length;
        for (int i = 0; i < length; i++) {
            textViews[i].setInputType(type);
        }
    }

    /**
     * 清除文本框
     */
    public void clearText() {
        editText.setText("");
        for (int i = 0; i < pwdLength; i++) {
            textViews[i].setText("");
        }
    }

    public void setOnTextFinishListener(OnTextFinishListener onTextFinishListener) {
        this.onTextFinishListener = onTextFinishListener;
    }


    public void setFocus() {
        editText.requestFocus();
        editText.setFocusable(true);
        showKeyBord(editText);
    }

    /**
     * 显示键盘
     *
     * @param view
     */
    public void showKeyBord(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);

    }

    /**
     * 密码文本输入监听
     */
    public interface OnTextFinishListener {
        /**
         * 密码输入完成
         *
         * @param str
         */
        void onFinish(String str);
    }

}
