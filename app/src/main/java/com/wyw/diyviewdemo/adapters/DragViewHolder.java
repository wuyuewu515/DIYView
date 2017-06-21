package com.wyw.diyviewdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wyw.diyviewdemo.R;

/**
 * 项目名称：DIYView
 * 类描述：
 * 创建人：伍跃武
 * 创建时间：2017/6/21 11:04
 */

public class DragViewHolder extends RecyclerView.ViewHolder {
    TextView tvDelete;
    TextView tvText;

    public DragViewHolder(View itemView) {
        super(itemView);
        tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
        tvText = (TextView) itemView.findViewById(R.id.tv_text);

    }
}