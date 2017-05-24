package com.wyw.diyviewdemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.wyw.diyviewdemo.R;

import java.text.DecimalFormat;

/**
 * 项目名称：DIYView
 * 类描述： 自定义水波球视图
 * 创建人：伍跃武
 * 创建时间：2017/5/24 11:17
 */
public class WaveBallView extends View {
    private Context context;
    //各种属性
    private int radius = 55; //半径
    private int textColor; //文字的颜色
    private int textSize; //文字大小
    private int progressColor; //
    private int radiusColor; //
    private float progress; //当前进度
    private float maxProgress = 100;// 最大进度
    private int width, height; //视图的长和宽
    private int minPadding;

    private Paint textPaint; //文字画笔
    private Paint circlePaint; //球画笔
    private Paint pathPaint;//波纹画笔
    private Path path = new Path();
    private DecimalFormat df = new DecimalFormat("0.0");

    private Bitmap bitmap;
    private Canvas bitmapCanvas;


    public WaveBallView(Context context) {
        this(context, null);
    }

    public WaveBallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveBallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.MyWaveProgressView, defStyleAttr, R.style.MyWaveProgressViewDefault);
        radius = (int) typedArray.getDimension(R.styleable.MyWaveProgressView_radius, 0);
        textColor = typedArray.getColor(R.styleable.MyWaveProgressView_progress_text_color, 0);
        textSize = (int) typedArray.getDimension(R.styleable.MyWaveProgressView_progress_text_size, 0);
        progressColor = typedArray.getColor(R.styleable.MyWaveProgressView_progress_color, 0);
        radiusColor = typedArray.getColor(R.styleable.MyWaveProgressView_radius_color, 0);
        progress = typedArray.getFloat(R.styleable.MyWaveProgressView_progress, 0);
        maxProgress = typedArray.getFloat(R.styleable.MyWaveProgressView_maxProgress, 100);
        typedArray.recycle();

        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        radius = Dp2Px(context, 100);
        progress = 70;
        maxProgress = 100;

        //文字
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setDither(true);

        //圆
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(radiusColor);
        circlePaint.setDither(true);

        //画波浪
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(progressColor);
        pathPaint.setDither(true);
        pathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //计算宽和高
        int exceptW = getPaddingLeft() + getPaddingRight() + 2 * radius;
        int exceptH = getPaddingTop() + getPaddingBottom() + 2 * radius;

        int width = resolveSize(exceptW, widthMeasureSpec);
        int hight = resolveSize(exceptH, heightMeasureSpec);

        int min = Math.min(width, hight);

        this.width = this.height = min;

        //计算半径,减去padding的最小值
        int minLR = Math.min(getPaddingLeft(), getPaddingRight());
        int minTB = Math.min(getPaddingTop(), getPaddingBottom());
        minPadding = Math.min(minLR, minTB);
        radius = (min - minPadding * 2) / 2;

        setMeasuredDimension(min, min);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmap);
        }
        bitmapCanvas.save();
        //移动坐标系
        bitmapCanvas.translate(minPadding, minPadding);
        //绘制圆
        bitmapCanvas.drawCircle(radius, radius, radius, circlePaint);

        //绘制path
        //重新绘制路线
        path.reset();
        float percent = progress * 1.0f / maxProgress;
        float y = (1 - percent) * radius * 2; //当前的水纹高度
        //移动到右上边
        path.moveTo(radius * 2, y);
        //移动到最右下方
        path.lineTo(radius * 2, radius * 2);
        //移动到最左下边
        path.lineTo(0, radius * 2);
        //移动到左上边
        // path.lineTo(0, y);
        //实现左右波动,根据progress来平移
        path.lineTo(-(1 - percent) * radius * 2, y);

        if (progress != 0.0f) {
            //根据直径绘制赛贝尔曲线的次数
            int count = radius * 1 / 60;
            //控制y点的坐标
            float point = (1 - percent) * 15;
            for (int i = 0; i < count; i++) {
                path.rQuadTo(15, -point, 30, 0);
                path.rQuadTo(15, point, 30, 0);
            }
        }
        path.close();
        bitmapCanvas.drawPath(path, pathPaint);

        //绘制文字
        String text = progress + "%";
        float textW = textPaint.measureText(text);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float baseLine = radius - (fontMetrics.ascent + fontMetrics.descent) / 2;
        bitmapCanvas.drawText(text, radius - textW / 2, baseLine, textPaint);

        bitmapCanvas.restore();
        canvas.drawBitmap(bitmap, 0, 0, null);
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
