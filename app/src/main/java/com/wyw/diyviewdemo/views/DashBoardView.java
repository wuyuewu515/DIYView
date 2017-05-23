package com.wyw.diyviewdemo.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目名称：DIYView
 * 类描述：仪表盘自定义view
 * 创建人：伍跃武
 * 创建时间：2017/5/23 10:43
 */
public class DashBoardView extends View {
    private Context context;

    private Paint mBgPaint = new Paint();
    private Paint mPaint = new Paint();

    private int mWidth;
    private int mHeight;
    private int mCenterX;
    private int mCenterY;

    private int outCircleColor = 0xFF97C6F3;//外圈颜色
    private int inCircleColor = 0xFF87BDF2;//内圈颜色
    private int scaleColor = 0xFFFFFFFF;//刻度颜色
    private int fontColor = 0xB0FFFFFF;//标注字体颜色

    private int mPadding;
    private int mSpace;
    private int mOuterCircleStrokeWidth; //外环的宽度
    private int mInnerCircleStrokeWidth;//内环的宽度
    private int mDialStrokeWidth;
    private int mDialFontSize;
    private int mDialSpace;
    private int mCurrentRotate;

    private float mLevelOnePerDegree;
    private float mLevelTwoPerDegree;
    private float mLevelThreePerDegree;
    private float mLevelFourPerDegree;
    private float mLevelFivePerDegree;

    /**
     * 信用分数
     */
    private float mValue;

    private String mDescript;
    private String mCreditLevel;


    public DashBoardView(Context context) {
        this(context, null);
    }

    public DashBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mCenterX = mWidth / 2;
        mCenterY = mCenterX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画表盘
        drawDial(canvas);

        //画点操作
        canvas.save();
        float value = mValue;
        float rotate = 0; //偏转角度
        if (value - 700 > 0) {
            rotate += (value - 700) / mLevelFivePerDegree;
            value = 700;
        }
        if (value - 650 > 0) {
            rotate += (value - 650) / mLevelFourPerDegree;
            value = 650;
        }
        if (value - 600 > 0) {
            rotate += (value - 600) / mLevelThreePerDegree;
            value = 600;
        }
        if (value - 550 > 0) {
            rotate += (value - 550) / mLevelTwoPerDegree;
            value = 550;
        }
        rotate += (value - 350) / mLevelOnePerDegree;

        canvas.rotate(-110 + mCurrentRotate, mCenterX, mCenterY);
        if (mCurrentRotate < rotate) {
            mCurrentRotate += 1;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            }, 10);
        }
        mPaint.setColor(scaleColor);
        canvas.drawCircle(mCenterX, mPadding, Dp2Px(context, 2.5f), mPaint);


        //画文字
        canvas.restore(); //防止文字更正一起旋转
        canvas.drawText(mDescript, mWidth / 2, mHeight - mPadding - Dp2Px(context, 5), mPaint);
        mPaint.setColor(scaleColor);
        mPaint.setTextSize(Dp2Px(context, 14));
        canvas.drawText(mCreditLevel, mCenterX, mCenterY, mPaint);
        mPaint.setTextSize(Dp2Px(context, 44));
        //动画分数，与上面一步的“点”同步
        if (mCurrentRotate < rotate) {
            float prePoint = (value - 350) / rotate;
            int currentPoint = 350 + (int) (prePoint * mCurrentRotate);
            canvas.drawText(String.valueOf(currentPoint), mCenterX, mCenterY - Dp2Px(context, 20), mPaint);
        } else {
            canvas.drawText(mValue + "", mCenterX, mCenterY - Dp2Px(context, 20), mPaint);
        }
    }

    /**
     * 画表盘
     *
     * @param canvas
     */
    private void drawDial(Canvas canvas) {

        /**
         * 开始画表盘
         */
        RectF rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        mBgPaint.setColor(outCircleColor);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeWidth(mOuterCircleStrokeWidth);
        //画外层圆弧
        canvas.drawArc(rectF, 160, 220, false, mBgPaint);
        canvas.save();

        //画内侧圆弧
        mBgPaint.setColor(inCircleColor);
        mBgPaint.setStrokeWidth(inCircleColor);
        mBgPaint.setStrokeWidth(mInnerCircleStrokeWidth);
        rectF = new RectF(mPadding + mSpace, mPadding + mSpace,
                mWidth - mPadding - mSpace, mWidth - mSpace - mPadding);
        canvas.drawArc(rectF, 160, 220, false, mBgPaint);
        canvas.save();

        canvas.rotate(-110, mCenterX, mCenterY);
        mBgPaint.setColor(scaleColor);
        mBgPaint.setStrokeWidth(mDialStrokeWidth);
        float startY = mPadding + mSpace - mInnerCircleStrokeWidth / 2f;
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(mDialFontSize);
        mPaint.setColor(fontColor);

        //刻度标志
        canvas.rotate(0, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, startY, mCenterX, startY + mInnerCircleStrokeWidth, mBgPaint);
        canvas.drawText("350", mCenterX, startY + mInnerCircleStrokeWidth + mDialFontSize + mDialSpace, mPaint);

        canvas.rotate(22, mCenterX, mCenterY);
        canvas.drawText("一般", mCenterX, startY + mInnerCircleStrokeWidth + mDialFontSize + mDialSpace, mPaint);

        canvas.rotate(22, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, startY, mCenterX, startY + mInnerCircleStrokeWidth, mBgPaint);
        canvas.drawText("550", mCenterX, startY + mInnerCircleStrokeWidth + mDialFontSize + mDialSpace, mPaint);


        canvas.rotate(22, mCenterX, mCenterY);
        canvas.drawText("中等", mCenterX, startY + mInnerCircleStrokeWidth + mDialFontSize + mDialSpace, mPaint);

        canvas.rotate(22, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, startY, mCenterX, startY + mInnerCircleStrokeWidth, mBgPaint);
        canvas.drawText("600", mCenterX, startY + mInnerCircleStrokeWidth + mDialFontSize + mDialSpace, mPaint);


        canvas.rotate(22, mCenterX, mCenterY);
        canvas.drawText("良好", mCenterX, startY + mInnerCircleStrokeWidth + mDialFontSize + mDialSpace, mPaint);

        canvas.rotate(22, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, startY, mCenterX, startY + mInnerCircleStrokeWidth, mBgPaint);
        canvas.drawText("650", mCenterX, startY + mInnerCircleStrokeWidth + mDialFontSize + mDialSpace, mPaint);


        canvas.rotate(22, mCenterX, mCenterY);
        canvas.drawText("优秀", mCenterX, startY + mInnerCircleStrokeWidth + mDialFontSize + mDialSpace, mPaint);

        canvas.rotate(22, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, startY, mCenterX, startY + mInnerCircleStrokeWidth, mBgPaint);
        canvas.drawText("700", mCenterX, startY + mInnerCircleStrokeWidth + mDialFontSize + mDialSpace, mPaint);


        canvas.rotate(22, mCenterX, mCenterY);
        canvas.drawText("极好", mCenterX, startY + mInnerCircleStrokeWidth + mDialFontSize + mDialSpace, mPaint);
        canvas.rotate(22, mCenterX, mCenterY);

        canvas.drawLine(mCenterX, startY, mCenterX, startY + mInnerCircleStrokeWidth, mBgPaint);
        canvas.drawText("900", mCenterX, startY + mInnerCircleStrokeWidth + mDialFontSize + mDialSpace, mPaint);

        canvas.restore();
    }

    private void init() {
        mPadding = Dp2Px(context, 2);
        mSpace = Dp2Px(context, 10);
        mOuterCircleStrokeWidth = Dp2Px(context, 3);
        mInnerCircleStrokeWidth = Dp2Px(context, 10);
        mDialStrokeWidth = Dp2Px(context, 1);
        mDialFontSize = Dp2Px(context, 10);
        mDialSpace = Dp2Px(context, 1);

        mPaint.setAntiAlias(true);
        mBgPaint.setAntiAlias(true);


        float sectionDegree = 220f / 5f;
        mLevelOnePerDegree = 200f / sectionDegree;
        mLevelTwoPerDegree = 50f / sectionDegree;
        mLevelThreePerDegree = 50f / sectionDegree;
        mLevelFourPerDegree = 50f / sectionDegree;
        mLevelFivePerDegree = 250f / sectionDegree;
    }

    /**
     * 设置分数
     *
     * @param value 分数
     */
    public void setmValue(float value) {
        this.mValue = value;
    }

    /**
     * 设置描述语言
     *
     * @param descript 描述
     */
    public void setmDescript(String descript) {
        this.mDescript = descript;
    }

    /**
     * 设置信用等级
     *
     * @param level 信用等级
     */
    public void setmCreditLevel(String level) {
        this.mCreditLevel = level;
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

}