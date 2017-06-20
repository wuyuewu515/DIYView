package com.wyw.diyviewdemo.interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * 项目名称：DIYView
 * 类描述： 卡片在回调时候的滑动监听
 * 创建人：伍跃武
 * 创建时间：2017/6/20 10:38
 */
public interface TanTanOnSwipListener<T> {

    /**
     * 卡片还在滑动时候的监听
     *
     * @param viewHolder 该卡片滑动的viewholder
     * @param ratio      滑动进度的比例
     * @param direction  滑动方向CardConfig.SWIPING_LEFT 为向左滑，CardConfig.SWIPING_RIGHT 为向右滑，
     *                   CardConfig.SWIPING_NONE 为不偏左也不偏右
     */
    void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction);

    /**
     * 卡片完全滑出去的时候监听
     *
     * @param viewHolder 该滑动卡片的viewholder
     * @param t          该卡片上的数据
     * @param diretion   滑动方向CardConfig.SWIPING_LEFT 为向左滑，CardConfig.SWIPING_RIGHT 为向右滑，
     *                   CardConfig.SWIPING_NONE 为不偏左也不偏右
     */
    void onSwiped(RecyclerView.ViewHolder viewHolder, T t, int diretion);


    /**
     * 所有卡片都滑完时候的回调
     */
    void onSwipedClear();
}
