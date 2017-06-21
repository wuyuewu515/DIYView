package com.wyw.diyviewdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.wyw.diyviewdemo.R;
import com.wyw.diyviewdemo.adapters.RecyclerDragAdapter;
import com.wyw.diyviewdemo.helpers.DragItemTouchHelperCallback;
import com.wyw.diyviewdemo.interfaces.RecyclerDragListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：DIYView
 * 类描述： 可拖拽的recyclerview
 * 创建人：伍跃武
 * 创建时间：2017/6/21 9:45
 */
public class RecyclDragRecyActivity extends AppCompatActivity implements RecyclerDragListener {
    @BindView(R.id.ry_hor)
    RecyclerView ryHor;
    @BindView(R.id.ry_ver)
    RecyclerView ryVer;

    private List<String> lists = new ArrayList<>();
    private RecyclerDragAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drag);
        ButterKnife.bind(this);

        initData();
        initViews();
    }

    private void initViews() {

//        LinearLayoutManager manager2 = new LinearLayoutManager(this);
//        manager2.setOrientation(LinearLayoutManager.VERTICAL);
     //   ryVer.setLayoutManager(manager2);

        GridLayoutManager manager = new GridLayoutManager(this, 3);

        ryHor.setLayoutManager(manager);
        if (adapter == null) {
            adapter = new RecyclerDragAdapter(lists, this, this);
            ryHor.setAdapter(adapter);
     //       ryVer.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        ItemTouchHelper.Callback callback = new DragItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(ryVer);
        itemTouchHelper.attachToRecyclerView(ryHor);
    }

    private void initData() {
        lists.clear();
        for (int i = 0; i < 20; i++) {
            lists.add(i + "");
        }

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

    }
}
