package com.wyw.diyviewdemo.helpers;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wyw.diyviewdemo.interfaces.OnPageTransformerListener;

import java.util.Set;
import java.util.TreeSet;

/**
 * 项目名称：DIYView
 * 类描述：卡片的transform
 * 创建人：伍跃武
 * 创建时间：2017/6/19 15:43
 */
public class CardPageTransformer implements ViewPager.PageTransformer {
    private Build mBuild;

    public CardPageTransformer(Build build) {
        this.mBuild = build;
    }

    public static Build getBuild() {
        return new Build();
    }

    @Override
    public void transformPage(View page, float position) {

        //判断方向
        if (mBuild.mOrientation == PageTransformerConfig.HORIZONTAL) {
            //水平方向  Horizontal
            transformHorizontal(page, position);
        }
    }

    private void transformHorizontal(View page, float position) {
        if (position <= 0.0f) { //被滑动的那页
            page.setTranslationX(0f);
            // 动画类型不为none才执行动画  Animation type is not 'none'
            if (!mBuild.mAnimationType.contains(PageTransformerConfig.NONE)) {
                //旋转 //Rotation
                if (mBuild.mAnimationType.contains(PageTransformerConfig.ROTATION)) {
                    //旋转角度  Rotation angle   -45° * 0.1 = -4.5°
                    page.setRotation((mBuild.mRotation * Math.abs(position)));
                    //X轴偏移 xAxis offset li:  300/3 * -0.1 = -10
                    page.setTranslationX((page.getWidth() / 3 * position));
                }

                //透明度 alpha
                if (mBuild.mAnimationType.contains(1)) {
                    //设置透明度  set alpha
                    page.setAlpha(mBuild.mAlpha - (mBuild.mAlpha * Math.abs(position)));
                }
            }
            //-----------------------动画 animation end
            //回调自定义动画 callback customize animation
            if (mBuild.onPageTransformerListener != null) {
                //回调
                mBuild.onPageTransformerListener.onPageTransformerListener(page, position);
            }
            //打开点击事件
            page.setClickable(true);
        }
        else {
           setHorizontalTransformPager(page, position);
            //屏蔽点击事件
            page.setClickable(false);
        }

    }
    /**
     * 设置禁止页面的样式
     * <p>set view type，set card style</p>
     *
     * @param page
     * @param position
     */
    private void setHorizontalTransformPager(View page, float position) {
        //缩放比例
        float scale = (page.getWidth() - mBuild.mScaleOffset * position) / (float) (page.getWidth());

        page.setScaleX(scale);
        page.setScaleY(scale);
        // why ? because the offset is to small,
        // 我感觉 它不够高，所以增加了比例
        float proportion = 1.5f;
        //开始判断类型 //type
        switch (mBuild.mViewType) {
            //底部 bottom
            case PageTransformerConfig.BOTTOM:
                page.setTranslationX((-page.getWidth() * position));
                page.setTranslationY((mBuild.mTranslationOffset * proportion) * position);
                break;
            //底左  bottom left
            case PageTransformerConfig.BOTTOM_LEFT:
                page.setTranslationX((-page.getWidth() * position) + ((mBuild.mTranslationOffset * proportion) * position));
                page.setTranslationY((mBuild.mTranslationOffset * proportion) * position);
                break;
            //底右  bottom right
            case PageTransformerConfig.BOTTOM_RIGHT:
                page.setTranslationX((-page.getWidth() * position) - ((mBuild.mTranslationOffset * proportion) * position));
                page.setTranslationY((mBuild.mTranslationOffset * proportion) * position);

                break;
            //右边  right
            case PageTransformerConfig.RIGHT:
                page.setTranslationX((-page.getWidth() * position) - ((mBuild.mTranslationOffset * proportion) * position));
                page.setTranslationY(0);
                break;
            //左边 left
            case PageTransformerConfig.LEFT:
                page.setTranslationX((-page.getWidth() * position) + ((mBuild.mTranslationOffset * proportion) * position));
                page.setTranslationY(0);
                break;
            //上面 top
            case PageTransformerConfig.TOP:
                page.setTranslationX((-page.getWidth() * position));
                page.setTranslationY(-((mBuild.mTranslationOffset * proportion) * position));
                break;
            //上左 top left
            case PageTransformerConfig.TOP_LEFT:
                page.setTranslationX((-page.getWidth() * position) + ((mBuild.mTranslationOffset * proportion) * position));
                page.setTranslationY(-((mBuild.mTranslationOffset * proportion) * position));
                break;
            //上右 top right
            case PageTransformerConfig.TOP_RIGHT:
                page.setTranslationX((-page.getWidth() * position) - ((mBuild.mTranslationOffset * proportion) * position));
                page.setTranslationY(-((mBuild.mTranslationOffset * proportion) * position));
                break;


        }


    }

    public static class Build {

        /**
         * 缩放偏移量
         */
        private int mScaleOffset = 40;
        /**
         * 偏移偏移量
         */
        private int mTranslationOffset = 40;

        /**
         * 旋转角度
         */
        private int mRotation = -45;

        /**
         * 透明度
         */
        private float mAlpha = 1f;

        /**
         * 视图类型
         */
        private int mViewType = PageTransformerConfig.BOTTOM;;

        /**
         * 动画集合
         */
        private Set<Integer> mAnimationType = new TreeSet<>();
        /**
         * 方向
         */
        private int mOrientation = PageTransformerConfig.HORIZONTAL;
        private OnPageTransformerListener onPageTransformerListener;

        public int getOrientation() {
            return mOrientation;
        }

        public Build setOrientation(int mOrientation) {
            this.mOrientation = mOrientation;
            return this;
        }

        public int getScaleOffset() {
            return mScaleOffset;
        }

        public Build setScaleOffset(int mScaleOffset) {
            this.mScaleOffset = mScaleOffset;
            return this;
        }

        public int getTranslationOffset() {
            return mTranslationOffset;
        }

        public Build setTranslationOffset(int mTranslationOffset) {
            this.mTranslationOffset = mTranslationOffset;
            return this;
        }

        public int getViewType() {
            return mViewType;
        }


        /**
         * 设置View的类型 或者说 样式
         *
         * @param mViewType
         * @return
         */
        public Build setViewType(int mViewType) {
            this.mViewType = mViewType;
            return this;
        }

        public Set<Integer> getAnimationType() {
            return mAnimationType;
        }

        /**
         * 增加 动画类型
         *
         * @param mAnimationType
         * @return
         */
        public Build addAnimationType(int... mAnimationType) {
            for (int type : mAnimationType) {
                this.mAnimationType.add(type);
            }
            return this;
        }


        /**
         * 完成创建
         *
         * @return
         */
        public ViewPager.PageTransformer create() {
            return new CardPageTransformer(this);
        }

        public int getRotation() {
            return mRotation;
        }

        public float getAlpha() {
            return mAlpha;
        }

        public Build setAlpha(float mAlpha) {
            this.mAlpha = mAlpha;
            return this;
        }

        public Build setRotation(int mRotation) {
            this.mRotation = mRotation;
            return this;
        }

        public Build setOnPageTransformerListener(@NonNull OnPageTransformerListener onPageTransformerListener) {
            this.onPageTransformerListener = onPageTransformerListener;
            return this;
        }
    }
}
