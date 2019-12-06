package com.hjl.emotionpicker.fragment;

import android.content.Context;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hjl.emotionpicker.R;
import com.hjl.emotionpicker.adapter.EmotionGridAdapter;
import com.hjl.emotionpicker.model.EmotionModel;
import com.hjl.emotionpicker.utils.EmotionPicker;
import com.hjl.emotionpicker.utils.SpanStringUtils;

import java.util.ArrayList;
import java.util.List;

public class EmotionFragment extends Fragment {

    private Context mContext;
    private int tabPage;

    private EditText mEditText;

    private List<EmotionModel> mAppMenuData = new ArrayList<>(); //全部的用户
    private EmotionGridAdapter mAdapter;

    public EmotionFragment() {

    }


    public static EmotionFragment newInstance(int tabPage) {
        EmotionFragment fragment = new EmotionFragment();
        Bundle args = new Bundle();
        args.putInt("tabPage", tabPage);

        fragment.setArguments(args);
        return fragment;
    }

    public void setEditText(EditText mEditText) {
        this.mEditText = mEditText;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        if (getArguments() != null) {
            tabPage = getArguments().getInt("tabPage");

        }
    }


    private RecyclerView mRecyclerView;
    private TextView mName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emotion, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        initView();

        return view;
    }


    private void initView() {
        int startNum = tabPage * 20;
        int endNum = tabPage * 20 + 20;

        List<EmotionModel> list = EmotionPicker.getInstance().getEmotionList();
        if (endNum > list.size()) {
            endNum = list.size();
        }
        mAppMenuData.clear();
        for (int i = startNum; i < endNum; i++) {
            mAppMenuData.add(list.get(i));
        }

        mAppMenuData.add(new EmotionModel("删除", R.drawable.emotion_delete));

        GridLayoutManager layoutManager = new GridLayoutManager(
                mContext, 7);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new EmotionGridAdapter(mContext, mAppMenuData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new EmotionGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("onClick", "onClick: " + mEditText.getText().toString());

                // 如果点击了表情,则添加到输入框中
                String emotionName = mAppMenuData.get(position).getName();

                // 获取当前光标位置,在指定位置上添加表情图片文本
                int curPosition = mEditText.getSelectionStart();
                StringBuilder sb = new StringBuilder(mEditText.getText().toString());
                sb.insert(curPosition, emotionName);

                // 特殊文字处理,将表情等转换一下
                mEditText.setText(SpanStringUtils.getEmotionContent(mContext, mEditText, sb.toString()));

                // 将光标设置到新增完表情的右侧
                mEditText.setSelection(curPosition + emotionName.length());

            }

            @Override
            public void onDeleteClick(View view, int position) {
                // 如果点击了最后一个回退按钮,则调用删除键事件
                mEditText.dispatchKeyEvent(new KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            }
        });
    }


}
