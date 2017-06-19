package com.wyw.diyviewdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyw.diyviewdemo.R;

/**
 * 项目名称：DIYView
 * 类描述：引导视图Fragment
 * 创建人：伍跃武
 * 创建时间：2017/6/19 14:12
 */
public class GuideFragment extends Fragment {

    public GuideFragment() {
    }

    public static GuideFragment newInstance(int pageIndex) {
        Bundle args = new Bundle();
        args.putInt("key", pageIndex);
        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guid_card, container, false);
        TextView tvText = (TextView) view.findViewById(R.id.tv_text);
        int pageIndex = getArguments().getInt("key", 0) + 1;
        tvText.setText(String.valueOf(pageIndex));

        return view;
    }


}
