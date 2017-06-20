package com.wyw.diyviewdemo.helpers;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 项目名称：DIYView
 * 类描述：layout管理
 * 创建人：伍跃武
 * 创建时间：2017/6/20 11:09
 */
public class TanTanManager extends RecyclerView.LayoutManager {

    private RecyclerView mRecyclerView;
    private ItemTouchHelper mMtemTouchHelper;

    public TanTanManager(RecyclerView mRecyclerView, ItemTouchHelper mMtemTouchHelper) {
        this.mRecyclerView = checkIsNull(mRecyclerView);
        this.mMtemTouchHelper = checkIsNull(mMtemTouchHelper);
    }


    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * item的布局
     *
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        //当数据源大于最大的显示数目时
        if (itemCount > TanTanCardConfig.DEFAUL_SHOW_ITEM) {
            for (int position = TanTanCardConfig.DEFAUL_SHOW_ITEM; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heghtSpace = getHeight() - getDecoratedRight(view);

                //recyclerView 布局
                layoutDecoratedWithMargins(view, widthSpace / 2, heghtSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heghtSpace / 2 + getBottomDecorationHeight(view));
                if (position == TanTanCardConfig.DEFAUL_SHOW_ITEM) {
                    view.setScaleX(1 - (position - 1) * TanTanCardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - (position - 1) * TanTanCardConfig.DEFAULT_SCALE);
                    view.setTranslationY((position - 1) *
                            view.getMeasuredHeight() / TanTanCardConfig.DEFAULT_TRANSLATE_Y);
                }else if (position > 0) {
                    view.setScaleX(1 - position * TanTanCardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * TanTanCardConfig.DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / TanTanCardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        } else {
            // 当数据源个数小于或等于最大显示数时
            for (int position = itemCount - 1; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                // recyclerview 布局
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));

                if (position > 0) {
                    view.setScaleX(1 - position * TanTanCardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * TanTanCardConfig.DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / TanTanCardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        }
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mMtemTouchHelper.startSwipe(childViewHolder);
            }
            return false;
        }
    };
}
