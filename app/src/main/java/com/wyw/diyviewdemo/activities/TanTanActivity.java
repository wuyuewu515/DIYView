package com.wyw.diyviewdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wyw.diyviewdemo.R;
import com.wyw.diyviewdemo.helpers.TanTanCardConfig;

import java.util.ArrayList;
import java.util.List;

import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

/**
 * 项目名称：DIYView
 * 类描述：左右两边都能滑动
 * 创建人：伍跃武
 * 创建时间：2017/6/20 9:51
 */
public class TanTanActivity extends AppCompatActivity {

    public static final String TAG = "TanTanActivity";
    private RecyclerView recyclerView;
    private List<String> lists = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tantan);

        initData();
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MyAdapter());

        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), lists);
        cardCallback.setOnSwipedListener(new OnSwipeListener<String>() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                ItemViewHolder myHolder = (ItemViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.1f);
                if (direction == TanTanCardConfig.SWIPING_LEFT) {
                    myHolder.dislikeImageView.setAlpha(Math.abs(ratio)*1.1f);
                    myHolder.likeImageView.setAlpha(0f);

                } else if (direction == TanTanCardConfig.SWIPING_RIGHT) {
                    myHolder.likeImageView.setAlpha(Math.abs(ratio)*1.1f);
                    myHolder.dislikeImageView.setAlpha(0f);

                } else {
                    myHolder.dislikeImageView.setAlpha(0f);
                    myHolder.likeImageView.setAlpha(0f);
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, String s, int diretion) {
                ItemViewHolder myHolder = (ItemViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1f);
                myHolder.dislikeImageView.setAlpha(0f);
                myHolder.likeImageView.setAlpha(0f);
                Toast.makeText(TanTanActivity.this, diretion == TanTanCardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipedClear() {

            }
        });
        ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);

    }

    /**
     * 创建数据
     */
    private void initData() {
        lists.clear();
        for (int i = 0; i < 10; i++) {
            lists.add(i + "");
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(TanTanActivity.this).inflate(R.layout.item_tantan_card
                    , parent, false);

            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.textView.setText(lists.get(position));
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView dislikeImageView;
        private TextView likeImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_text);
            dislikeImageView = (TextView) itemView.findViewById(R.id.tv_dislike);
            likeImageView = (TextView) itemView.findViewById(R.id.tv_like);
        }
    }
}


