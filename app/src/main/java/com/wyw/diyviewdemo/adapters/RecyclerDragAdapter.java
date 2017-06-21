package com.wyw.diyviewdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wyw.diyviewdemo.R;
import com.wyw.diyviewdemo.interfaces.ItemTouchListener;
import com.wyw.diyviewdemo.interfaces.RecyclerDragListener;

import java.util.Collections;
import java.util.List;

/**
 * 项目名称：DIYView
 * 类描述：拖拽的瀑布流适配器
 * 创建人：伍跃武
 * 创建时间：2017/6/21 10:06
 */
public class RecyclerDragAdapter<T> extends RecyclerView.Adapter<DragViewHolder> implements ItemTouchListener {

    private List<T> dataLists;
    private Context context;
    private RecyclerDragListener mDragListener;

    public RecyclerDragAdapter(List<T> dataLists, Context context, RecyclerDragListener dragListener) {
        this.dataLists = dataLists;
        this.context = context;
        this.mDragListener = dragListener;
    }

    @Override
    public DragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_drag_card, parent, false);
        return new DragViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DragViewHolder holder, int position) {
        holder.tvText.setText(dataLists.get(position) + "");
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //开始执行拖拽动画
                    mDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataLists != null && dataLists.size() > 0) {
            return dataLists.size();
        } else
            return 0;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(dataLists, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public boolean onItemRemove(int position) {
        dataLists.remove(position);
        notifyItemRemoved(position);
        return true;
    }
}


