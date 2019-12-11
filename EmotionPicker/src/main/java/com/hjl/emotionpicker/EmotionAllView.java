package com.hjl.emotionpicker;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hjl.emotionpicker.adapter.EmotionGridAdapter;
import com.hjl.emotionpicker.adapter.EmotionGridAllAdapter;
import com.hjl.emotionpicker.model.EmotionModel;
import com.hjl.emotionpicker.utils.DimenUtil;
import com.hjl.emotionpicker.utils.EmotionPicker;
import com.hjl.emotionpicker.utils.GridSpacingItemDecoration;
import com.hjl.emotionpicker.utils.SpanStringUtils;

import java.util.ArrayList;
import java.util.List;


public class EmotionAllView extends LinearLayout {
    private static final String TAG = "CustomerEditText";
    private Context mContext;

    public EmotionAllView(Context context) {
        super(context);
    }

    public EmotionAllView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public EmotionAllView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private RecyclerView mRecyclerView;
    private LinearLayout mDeleteBtn;



    private List<EmotionModel> mAppMenuData = new ArrayList<>(); //全部的用户
    private EmotionGridAllAdapter mAdapter;



    private void init(AttributeSet attrs) {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); //第一个参数是宽,第二个参数是高
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.view_emotion_all, null);
        view.setLayoutParams(lp);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mDeleteBtn = (LinearLayout) view.findViewById(R.id.delete_btn);

        mDeleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 如果点击了最后一个回退按钮,则调用删除键事件
                mEditText.dispatchKeyEvent(new KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            }
        });
        mDeleteBtn.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mBtnTouchMap.put(v.getId(), true);
                mHandler.sendEmptyMessage(LONG_CLICK);
                return false;
            }
        });
        mDeleteBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 手指举起
                        mBtnTouchMap.put(v.getId(), false);
                        break;
                }

                return false;
            }
        });



        addView(view);

    }
    /** 长按标记，记录每个按钮是否正在按着 */
    private SparseArray<Boolean> mBtnTouchMap = new SparseArray<>();
    /** handler标记 */
    private static final int LONG_CLICK = 10001;
    /** 处理 */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LONG_CLICK:
                    for (int i = 0; i < mBtnTouchMap.size(); i++) {
                        int viewId = mBtnTouchMap.keyAt(i);
                        // 长按结束，就不再继续往下走了
                        if (!mBtnTouchMap.valueAt(i)) continue;

                        deleteText();

                        // 每隔150毫秒做一次
                        mHandler.sendEmptyMessageDelayed(LONG_CLICK, 50);
                    }
                    break;
            }
        }
    };


    private void deleteText() {
        // 如果点击了最后一个回退按钮,则调用删除键事件
        mEditText.dispatchKeyEvent(new KeyEvent(
                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
    }



    private EditText mEditText;

    public void init(EditText mEditText) {
        this.mEditText = mEditText;
        initEmotionView();
    }

    private void initEmotionView() {

        mAppMenuData.clear();
        List<EmotionModel> list = EmotionPicker.getInstance().getEmotionList();
        for (EmotionModel item : list) {
            mAppMenuData.add(item);
        }
        mAppMenuData.add(new EmotionModel("删除",R.drawable.emotion_delete));

        GridLayoutManager layoutManager = new GridLayoutManager(
                mContext, 8);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case 0:
                        return 8; // 宽度为12， item满屏
                    case 1:
                        return 1; // 宽度为12， item满屏
                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(8, DimenUtil.dp2px(mContext, 8), false));
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        mAdapter = new EmotionGridAllAdapter(mContext, mAppMenuData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new EmotionGridAllAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
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

        });
    }


}
