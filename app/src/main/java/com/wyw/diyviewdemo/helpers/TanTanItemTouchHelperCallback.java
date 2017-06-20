package com.wyw.diyviewdemo.helpers;

import android.graphics.Canvas;
import android.nfc.cardemulation.OffHostApduService;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.wyw.diyviewdemo.interfaces.TanTanOnSwipListener;

import java.util.List;


/**
 * 项目名称：DIYView
 * 类描述：
 * 创建人：伍跃武
 * 创建时间：2017/6/20 10:25
 */
public class TanTanItemTouchHelperCallback<T> extends ItemTouchHelper.Callback {

    private final RecyclerView.Adapter adapter;
    private List<T> dataList;
    private TanTanOnSwipListener<T> mListener;

    public TanTanItemTouchHelperCallback(List<T> dataList, RecyclerView.Adapter adapter) {
        this.dataList = checkIsNull(dataList);
        this.adapter = checkIsNull(adapter);
    }

    public TanTanItemTouchHelperCallback(RecyclerView.Adapter adapter, List<T> dataList, TanTanOnSwipListener<T> mListener) {
        this.adapter = checkIsNull(adapter);
        this.dataList = checkIsNull(dataList);
        this.mListener = checkIsNull(mListener);
    }

    //非空判断
    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    public void setmListener(TanTanOnSwipListener<T> mListener) {
        this.mListener = mListener;
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
        int dragFlags = 0;
        int swipeFlags = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof TanTanManager) {
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 当拖动的时候调用该方法
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * 当滑动的时候调用该方法
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        viewHolder.itemView.setOnTouchListener(null);
        int layoutPosition = viewHolder.getLayoutPosition();
        T remove = dataList.remove(layoutPosition);
        adapter.notifyDataSetChanged();
        if (mListener != null) {
            mListener.onSwiped(viewHolder, remove, direction == ItemTouchHelper.LEFT ?
                    TanTanCardConfig.SWIPED_LEFT : TanTanCardConfig.SWIPED_RIGHT);
        }

        //数据没有的时候进行回调
        if (adapter.getItemCount() == 0) {
            if (mListener != null) {
                mListener.onSwipedClear();
            }
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float ratio = dX / getThreshold(recyclerView, viewHolder);
            if (ratio > 1) {
                ratio = 1;
            } else if (ratio < -1) {
                ratio = -1;
            }
            itemView.setRotation(ratio * TanTanCardConfig.DEFAULT_ROTATE_DEGREE);
            int childCount = recyclerView.getChildCount();
            if (childCount > TanTanCardConfig.DEFAUL_SHOW_ITEM) {
                for (int position = 1; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(index);
                    view.setScaleX(1 - index * TanTanCardConfig.DEFAULT_SCALE
                            + Math.abs(ratio) * TanTanCardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - index * TanTanCardConfig.DEFAULT_SCALE
                            + Math.abs(ratio) * TanTanCardConfig.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio))
                            * itemView.getMeasuredHeight() / TanTanCardConfig.DEFAULT_TRANSLATE_Y);
                }
            } else {
                // 当数据源个数小于或等于最大显示数时
                for (int position = 0; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1 - index * TanTanCardConfig.DEFAULT_SCALE
                            + Math.abs(ratio) * TanTanCardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - index * TanTanCardConfig.DEFAULT_SCALE
                            + Math.abs(ratio) * TanTanCardConfig.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio))
                            * itemView.getMeasuredHeight() / TanTanCardConfig.DEFAULT_TRANSLATE_Y);
                }
            }
            if (mListener != null) {
                if (ratio != 0) {
                    mListener.onSwiping(viewHolder, ratio, ratio < 0 ? TanTanCardConfig.SWIPING_LEFT : TanTanCardConfig.SWIPING_RIGHT);
                } else {
                    mListener.onSwiping(viewHolder, ratio, TanTanCardConfig.SWIPING_NONE);
                }
            }
        }
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setRotation(0f);
    }

    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

}
