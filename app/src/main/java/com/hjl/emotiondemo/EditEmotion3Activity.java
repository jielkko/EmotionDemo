package com.hjl.emotiondemo;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjl.emotiondemo.common.ContextUtil;
import com.hjl.emotiondemo.utils.EmojiKeyboard;
import com.hjl.emotiondemo.utils.KeyBoardUtils;
import com.hjl.emotiondemo.utils.PanelKeyBoardSPUtils;
import com.hjl.emotiondemo.utils.ScreenUtils;
import com.hjl.emotionpicker.EmotionAllView;
import com.lai.library.ButtonStyle;

public class EditEmotion3Activity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    private LinearLayout mChatContainer;
    private TextView mIsNetwork;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private CheckBox mVoiceButton;
    private LinearLayout mRlEditbarBg;
    private EditText mMyEditText;
    private TextView mRecorderButton;
    private CheckBox mEmotionButton;
    private CheckBox mMoreButton;
    private ButtonStyle mSendBtn;
    private LinearLayout mPanelLayout;
    private LinearLayout mEmotionLayout;
    private EmotionAllView mEmotionView;
    private LinearLayout mMoreLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emotion3);

        findById();
        initView();
    }

    private void findById() {
        mChatContainer = (LinearLayout) findViewById(R.id.chat_container);
        mIsNetwork = (TextView) findViewById(R.id.isNetwork);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mVoiceButton = (CheckBox) findViewById(R.id.voice_button);
        mRlEditbarBg = (LinearLayout) findViewById(R.id.rl_editbar_bg);
        mMyEditText = (EditText) findViewById(R.id.my_editText);
        mRecorderButton = (TextView) findViewById(R.id.recorder_button);
        mEmotionButton = (CheckBox) findViewById(R.id.emotion_button);
        mMoreButton = (CheckBox) findViewById(R.id.more_button);
        mSendBtn = (ButtonStyle) findViewById(R.id.send_btn);
        mPanelLayout = (LinearLayout) findViewById(R.id.panel_layout);
        mEmotionLayout = (LinearLayout) findViewById(R.id.emotion_layout);
        mEmotionView = (EmotionAllView) findViewById(R.id.emotion_view);
        mMoreLayout = (LinearLayout) findViewById(R.id.more_layout);

        mRefreshLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "mRefreshLayouton    Click: 点击");
            }
        });


        mMyEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaneSelect(PaneKeyBard, null);
            }
        });


        mVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaneSelect(PaneVoice, mVoiceButton);
            }
        });

        mEmotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaneSelect(PaneEmotion, mEmotionButton);
            }
        });

        mMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaneSelect(PaneMore, mMoreButton);
            }
        });


    }

    /**
     * 如果之前没有保存过键盘高度值
     * 则在进入Activity时自动打开键盘，并把高度值保存下来
     */
    private void initView() {
        if (!PanelKeyBoardSPUtils.contains(this, KEY_SOFT_KEYBOARD_HEIGHT)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showSoftKeyboard(true);
                }
            }, 200);
        }
        getSoftKeyboardHeightLocalValue();

        mEmotionView.init(mMyEditText);
    }


    //private int PaneStatus = 0;  //0 面板隐藏  1 输入法  2 语音
    private final int PaneHide = 0; //面板隐藏
    private final int PaneKeyBard = 1; //输入法
    private final int PaneVoice = 2; //语音
    private final int PaneEmotion = 3; //表情包
    private final int PaneMore = 4; //更多

    private void PaneSelect(int status, CheckBox mCheckBox) {


        if (status == PaneVoice) {
            if (mCheckBox.isChecked()) {
                mRecorderButton.setVisibility(View.VISIBLE);
                mMyEditText.setVisibility(View.GONE);
                hideSoftKeyboard();
               /* lockContentViewHeight();
                hidePanel(false);
                unlockContentViewHeight();*/
            } else {
                mRecorderButton.setVisibility(View.GONE);
                mMyEditText.setVisibility(View.VISIBLE);
                showSoftKeyboard(false);
                /*lockContentViewHeight();
                hidePanel(true);
                unlockContentViewHeight();*/
            }
        }
        if (status == PaneEmotion) {

            if (mPanelLayout.getVisibility() == View.VISIBLE) {
                lockContentViewHeight();
                hidePanel(true);
                unlockContentViewHeight();
            } else {
                if (isSoftKeyboardShown()) {
                    lockContentViewHeight();
                    showPanel();
                    unlockContentViewHeight();
                } else {
                    showPanel();
                }
            }

        }

        if (status == PaneMore) {

            if (mPanelLayout.getVisibility() == View.VISIBLE) {
                lockContentViewHeight();
                hidePanel(true);
                unlockContentViewHeight();
            } else {
                if (isSoftKeyboardShown()) {
                    lockContentViewHeight();
                    showPanel();
                    unlockContentViewHeight();
                } else {
                    showPanel();
                }
            }

        }

        mEmotionLayout.setVisibility(View.GONE);
        mMoreLayout.setVisibility(View.GONE);

        switch (status) {
            case PaneHide:
                //面板隐藏

                break;
            case PaneKeyBard:
                //输入法
                lockContentViewHeight();
                hidePanel(true);
                unlockContentViewHeight();
                break;
            case PaneVoice:
                //语音
                //mVoiceButton.setChecked(false);
                mEmotionButton.setChecked(false);
                mMoreButton.setChecked(false);
                break;
            case PaneEmotion:
                //表情包
                mVoiceButton.setChecked(false);
                //mEmotionButton.setChecked(false);
                mMoreButton.setChecked(false);

                mEmotionLayout.setVisibility(View.VISIBLE);
                break;
            case PaneMore:
                //更多
                mVoiceButton.setChecked(false);
                mEmotionButton.setChecked(false);
                //mMoreButton.setChecked(false);

                mMoreLayout.setVisibility(View.VISIBLE);
                break;
        }
    }


    private static final String Panel_KEYBOARD = "PanelKeyboard";

    private static final String KEY_SOFT_KEYBOARD_HEIGHT = "SoftKeyboardHeight";

    private static final int SOFT_KEYBOARD_HEIGHT_DEFAULT = 654;

    private Handler handler = new Handler();

    /**
     * 显示更多面板
     */
    public void showPanel() {

        int softKeyboardHeight = getSoftKeyboardHeight();
        if (softKeyboardHeight == 0) {
            softKeyboardHeight = getSoftKeyboardHeightLocalValue();
        } else {
            hideSoftKeyboard();
        }
        Log.e("TAG-softKeyboardHeight", softKeyboardHeight + "");
        mPanelLayout.getLayoutParams().height = softKeyboardHeight;
        mPanelLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏更多面板，同时指定是否随后开启键盘
     */
    public void hidePanel(boolean showSoftKeyboard) {

        mPanelLayout.setVisibility(View.GONE);
        if (showSoftKeyboard) {
            showSoftKeyboard(false);

        }

    }

    /**
     * 令编辑框获取焦点并显示键盘
     */
    private void showSoftKeyboard(boolean saveSoftKeyboardHeight) {
        mMyEditText.setVisibility(View.VISIBLE);
        //获取焦点
        mMyEditText.setFocusable(true);
        mMyEditText.setFocusableInTouchMode(true);
        mMyEditText.requestFocus();//获取焦点 光标出现

        KeyBoardUtils.openKeybord(mMyEditText);
        if (saveSoftKeyboardHeight) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSoftKeyboardHeight();
                }
            }, 200);
        }
    }

    /**
     * 隐藏键盘
     */
    private void hideSoftKeyboard() {
        KeyBoardUtils.closeKeybord(mMyEditText);
    }

    /**
     * 锁定内容View以防止跳闪
     */
    private void lockContentViewHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mChatContainer.getLayoutParams();
        layoutParams.height = mChatContainer.getHeight();
        layoutParams.weight = 0;
    }

    /**
     * 释放锁定的内容View
     */
    private void unlockContentViewHeight() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) mChatContainer.getLayoutParams()).weight = 1;
            }
        }, 200);
    }

    /**
     * 获取键盘的高度
     */
    private int getSoftKeyboardHeight() {


        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //屏幕当前可见高度，不包括状态栏
        int displayHeight = rect.bottom - rect.top;
        //屏幕可用高度
        int availableHeight = ScreenUtils.getAvailableScreenHeight(this);
        //用于计算键盘高度
        int softInputHeight = availableHeight - displayHeight - ScreenUtils.getStatusBarHeight(this);
        Log.e("TAG-di", displayHeight + "");
        Log.e("TAG-av", availableHeight + "");
        Log.e("TAG-so", softInputHeight + "");
        if (softInputHeight > 0) {
            // 因为考虑到用户可能会主动调整键盘高度，所以只能是每次获取到键盘高度时都将其存储起来
            if (!PanelKeyBoardSPUtils.contains(this, KEY_SOFT_KEYBOARD_HEIGHT)) {
                PanelKeyBoardSPUtils.put(this, KEY_SOFT_KEYBOARD_HEIGHT, softInputHeight);
            }
        }

        return softInputHeight;
    }

    /**
     * 获取本地存储的键盘高度值或者是返回默认值
     */
    private int getSoftKeyboardHeightLocalValue() {

        int value = (int) PanelKeyBoardSPUtils.get(this, KEY_SOFT_KEYBOARD_HEIGHT, SOFT_KEYBOARD_HEIGHT_DEFAULT);
        Log.d(TAG, "键盘高度: " + value);
        return value;
    }

    /**
     * 判断是否显示了键盘
     */
    private boolean isSoftKeyboardShown() {
        return getSoftKeyboardHeight() != 0;
    }


}
