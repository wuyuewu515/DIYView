package com.wyw.diyviewdemo.interfaces;


import android.support.v7.widget.RecyclerView;

/**
 * 项目名称：DIYView
 * 类描述：拖拽接口
 * 创建人：伍跃武
 * 创建时间：2017/6/21 10:02
 */
public interface RecyclerDragListener {
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
