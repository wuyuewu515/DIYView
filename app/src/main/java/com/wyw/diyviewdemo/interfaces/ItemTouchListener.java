package com.wyw.diyviewdemo.interfaces;

/**
 * 项目名称：DIYView
 * 类描述：
 * 创建人：伍跃武
 * 创建时间：2017/6/21 11:12
 */
public interface ItemTouchListener {
    /**
     * 当item 上下移动时调用
     *
     * @param fromPosition 换之前的位置
     * @param toPosition   换之后的位置
     * @return
     */
    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * 当item左右移动时调动
     *
     * @param position
     * @return
     */
    boolean onItemRemove(int position);
}