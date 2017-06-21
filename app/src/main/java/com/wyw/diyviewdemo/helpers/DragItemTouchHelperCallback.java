package com.wyw.diyviewdemo.helpers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.wyw.diyviewdemo.R;
import com.wyw.diyviewdemo.interfaces.ItemTouchListener;

/**
 * 项目名称：DIYView
 * 类描述：
 * 创建人：伍跃武
 * 创建时间：2017/6/21 10:27
 */
public class DragItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchListener dragListener;

    public DragItemTouchHelperCallback(ItemTouchListener dragListener) {
        this.dragListener = dragListener;
    }

    /**
     * 设置支持的拖动和滑动的方向
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //支持上下
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        //支持左右滑动
        //    int swipFlags = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;

        int flags = makeMovementFlags(dragFlags, 0);
        return flags;
    }


    /**
     * 当上下拖动的时候调用该方法
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            //两者类型不一致，不能交换
            return false;
        }
        boolean result = dragListener.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return result;
    }

    /**
     * 当左右滑动的时候调用该方法
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        dragListener.onItemRemove(position);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //不是空闲状态，即选中的状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 恢复状态
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        super.clearView(recyclerView, viewHolder);
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //在左右滑动时，让item的透明度随着移动而改变，并缩放
        float alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setScaleX(alpha);
            viewHolder.itemView.setScaleY(alpha);

        }
        //防止item复用变成透明
        if (alpha <= 0) {
            viewHolder.itemView.setAlpha(1);
            viewHolder.itemView.setScaleX(1);
            viewHolder.itemView.setScaleY(1);

        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
