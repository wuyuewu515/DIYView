package com.wyw.diyviewdemo.helpers;

/**
 * 项目名称：DIYView
 * 类描述：探探卡片的配置设置
 * 创建人：伍跃武
 * 创建时间：2017/6/20 9:55
 */
public final class TanTanCardConfig {

    /**
     * 默认显示的卡片数目
     */
    public static final int DEFAUL_SHOW_ITEM = 3;

    /**
     * 默认的缩放比例
     */
    public static final float DEFAULT_SCALE = 0.1f;

    /**
     * 默认y轴的偏移量按照14等分计算
     */
    public static final int DEFAULT_TRANSLATE_Y = 14;

    /**
     * 卡片滑动时默认的倾斜角度
     */
    public static final float DEFAULT_ROTATE_DEGREE = 45f;

    /**
     * 滑动时候卡片不偏左也不偏右
     */
    public static final int SWIPING_NONE = 1;

    /**
     * 向左边滑出时候
     */
    public static final int SWIPING_LEFT = 1 << 2;

    /**
     * 向右边滑出时候
     */
    public static final int SWIPING_RIGHT = 1 << 3;

    /**
     * 卡片从左边滑出
     */
    public static final int SWIPED_LEFT = 1;
    /**
     * 卡片从右边滑出
     */
    public static final int SWIPED_RIGHT = 1 << 2;

}
