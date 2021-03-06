package com.hjl.emotiondemo;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjl.emotiondemo.common.ContextUtil;
import com.hjl.emotiondemo.utils.KeyBoardUtils;
import com.hjl.emotiondemo.utils.NavigationBarUtils;
import com.hjl.emotiondemo.utils.PanelKeyBoardSPUtils;
import com.hjl.emotionpicker.EmotionAllView;
import com.lai.library.ButtonStyle;

public class EditEmotion3Activity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    private LinearLayout mParent;
    private LinearLayout mChatContainer;
    private TextView mIsNetwork;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayout mZhezhaoceng;
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

    private Boolean isShowKeyKeyBoard = false;
    private int keyBoardHeightChange = 0;  //实时高度
    private int keyBoardHeight = 0;   //默认弹出高度

    private float scrollX = 0L;   //默认弹出高度
    private float scrollY = 0L;   //默认弹出高度

    private void findById() {
        mParent = (LinearLayout) findViewById(R.id.parent);
        mChatContainer = (LinearLayout) findViewById(R.id.chat_container);
        mIsNetwork = (TextView) findViewById(R.id.isNetwork);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mZhezhaoceng = (LinearLayout) findViewById(R.id.zhezhaoceng);
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


        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    scrollX = event.getX();
                    scrollY = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (v.getId() != 0 && Math.abs(scrollX - event.getX()) <= 5 && Math.abs(scrollY - event.getY()) <= 5) {
                        //recyclerView空白处点击事件
                        Log.d(TAG, "recyclerView空白处点击事件");
                    }
                }
                return false;
            }
        });


        mMyEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获得焦点
                if (mPaneStatus == PaneEmotion || mPaneStatus == PaneMore) {
                    mVoiceButton.setChecked(false);
                    mEmotionButton.setChecked(false);
                    mMoreButton.setChecked(false);

                    //输入法
                    mPanelLayout.setVisibility(View.GONE);
                    //弹出键盘
                    showSoftKeyboard();

                    mZhezhaoceng.setVisibility(View.VISIBLE);
                }

                if (mPaneStatus == PaneHide) {
                    mZhezhaoceng.setVisibility(View.VISIBLE);
                }
                if (mPaneStatus == PaneVoice) {
                    mZhezhaoceng.setVisibility(View.VISIBLE);
                }
            }
        });
        mMyEditText.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    // 获得焦点
                    if (mPaneStatus == PaneEmotion || mPaneStatus == PaneMore) {
                        mVoiceButton.setChecked(false);
                        mEmotionButton.setChecked(false);
                        mMoreButton.setChecked(false);


                        if (isShowKeyKeyBoard) {
                            lockContentViewHeight();
                            mPanelLayout.setVisibility(View.GONE);
                            //弹出键盘
                            showSoftKeyboard();
                            unlockContentViewHeight();
                        } else {
                            mPanelLayout.setVisibility(View.GONE);
                            //弹出键盘
                            showSoftKeyboard();
                        }
                        mZhezhaoceng.setVisibility(View.VISIBLE);
                    }
                    if (mPaneStatus == PaneHide) {
                        mZhezhaoceng.setVisibility(View.VISIBLE);
                    }
                    if (mPaneStatus == PaneVoice) {
                        mZhezhaoceng.setVisibility(View.VISIBLE);
                    }
                } else {

                    // 失去焦点

                }

            }


        });

        mMyEditText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);//获取当前界面可视部分
                int screenHeight = getWindow().getDecorView().getRootView().getHeight();//获取屏幕高度
                int heiDifference = screenHeight - rect.bottom;//获取键盘高度，键盘没有弹出时，高度为0，键盘弹出时，高度为正数
                Log.d("Keyboard Size", "Size: " + heiDifference);

                int virtualBarHeight = NavigationBarUtils.getNavigationBarHeightIfRoom(EditEmotion3Activity.this);
                Log.d(TAG, "虚拟导航栏: " + virtualBarHeight);

                if (heiDifference == 0) {

                } else {
                    if (virtualBarHeight > 0) {
                        heiDifference = heiDifference - virtualBarHeight;
                    }
                }

                Log.d(TAG, "实际输入法高度: " + heiDifference);
                keyBoardHeightChange = heiDifference;
                if (heiDifference > 0) {
                    //todo：键盘弹出时
                    Log.d(TAG, "键盘弹出时: ");
                    isShowKeyKeyBoard = true;
                    keyBoardHeight = heiDifference;
                    // 因为考虑到用户可能会主动调整键盘高度，所以只能是每次获取到键盘高度时都将其存储起来
                    if (keyBoardHeight > 100) {
                        PanelKeyBoardSPUtils.put(ContextUtil.getContext(), KEY_SOFT_KEYBOARD_HEIGHT, keyBoardHeight);
                    }
                    mZhezhaoceng.setVisibility(View.VISIBLE);
                } else {
                    //todo:键盘没有弹出时
                    Log.d(TAG, "键盘没有弹出时: ");
                    isShowKeyKeyBoard = false;
                    mZhezhaoceng.setVisibility(View.GONE);

                }
            }
        });
        mZhezhaoceng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "虚拟卡: ");

                mPanelLayout.setVisibility(View.GONE);
                KeyBoardUtils.closeKeybord(mMyEditText);
                mZhezhaoceng.setVisibility(View.GONE);

            }
        });

        mVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaneStatus = PaneVoice;
                PaneSelect(mVoiceButton);
            }
        });

        mEmotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaneStatus = PaneEmotion;
                PaneSelect(mEmotionButton);
            }
        });

        mMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaneStatus = PaneMore;
                PaneSelect(mMoreButton);
            }
        });


    }

    /**
     * 如果之前没有保存过键盘高度值
     * 则在进入Activity时自动打开键盘，并把高度值保存下来
     */
    private void initView() {

        initPanelView();


    }

    /**
     * 初始化显示面板高度
     */
    public void initPanelView() {

        if (PanelKeyBoardSPUtils.contains(this, KEY_SOFT_KEYBOARD_HEIGHT)) {

            //高度数据存在
            keyBoardHeight = getSoftKeyboardHeightLocalValue();
            keyBoardHeightChange = keyBoardHeight;
            mPanelLayout.getLayoutParams().height = keyBoardHeight;
        } else {
            mPanelHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //弹出键盘
                    showSoftKeyboard();
                }
            }, 200);
        }

        mEmotionView.init(mMyEditText);
    }


    private int mPaneStatus = 0;  //0 面板隐藏  1 输入法  2 语音
    private final int PaneHide = 0; //面板隐藏
    private final int PaneVoice = 1; //语音
    private final int PaneEmotion = 2; //表情包
    private final int PaneMore = 3; //更多

    private void PaneSelect(CheckBox mCheckBox) {


        if (mPaneStatus == PaneVoice) {

            if (mCheckBox.isChecked()) {
                //语音

                mRecorderButton.setVisibility(View.VISIBLE);
                mMyEditText.setVisibility(View.GONE);

                if (mPanelLayout.getHeight() > 0) {
                    mPanelLayout.getLayoutParams().height = 0;
                    mPanelLayout.setVisibility(View.GONE);
                    //隐藏键盘
                    hideSoftKeyboard();
                } else {
                    lockContentViewHeight();
                    mPanelLayout.getLayoutParams().height = 0;
                    mPanelLayout.setVisibility(View.GONE);
                    //隐藏键盘
                    hideSoftKeyboard();
                    unlockContentViewHeight();
                }

            } else {
                //输入法
                mRecorderButton.setVisibility(View.GONE);
                mMyEditText.setVisibility(View.VISIBLE);

                //弹出键盘
                showSoftKeyboard();
                mZhezhaoceng.setVisibility(View.VISIBLE);
            }

        }

        if (mPaneStatus == PaneEmotion || mPaneStatus == PaneMore) {
            if (mCheckBox.isChecked()) {
                if (isShowKeyKeyBoard) {
                    lockContentViewHeight();
                    showPanel();
                    unlockContentViewHeight();
                } else {
                    showPanel();
                }
                mZhezhaoceng.setVisibility(View.VISIBLE);
            }else{
                if (isShowKeyKeyBoard) {
                    lockContentViewHeight();
                    hidePanel();
                    unlockContentViewHeight();
                } else {
                    hidePanel();
                }

            }
/*
            if (mPanelLayout.getVisibility() == View.VISIBLE) {
                lockContentViewHeight();
                hidePanel();
                unlockContentViewHeight();
            } else {
                if (isShowKeyKeyBoard) {
                    lockContentViewHeight();
                    showPanel();
                    unlockContentViewHeight();
                } else {
                    showPanel();
                }
            }*/

        }


        mEmotionLayout.setVisibility(View.GONE);
        mMoreLayout.setVisibility(View.GONE);

        switch (mPaneStatus) {
            case PaneHide:
                //面板隐藏

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

                mRecorderButton.setVisibility(View.GONE);
                mMyEditText.setVisibility(View.VISIBLE);

                mEmotionLayout.setVisibility(View.VISIBLE);
                break;
            case PaneMore:
                //更多
                mVoiceButton.setChecked(false);
                mEmotionButton.setChecked(false);
                //mMoreButton.setChecked(false);

                mRecorderButton.setVisibility(View.GONE);
                mMyEditText.setVisibility(View.VISIBLE);

                mMoreLayout.setVisibility(View.VISIBLE);
                break;
        }
    }


    private static final String KEY_SOFT_KEYBOARD_HEIGHT = "SoftKeyboardHeight1";

    private static final int SOFT_KEYBOARD_HEIGHT_DEFAULT = 654;

    private Handler mPanelHandler = new Handler();

    /**
     * 显示更多面板
     */
    public void showPanel() {

        int softKeyboardHeight = keyBoardHeightChange;
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
    public void hidePanel() {

        mPanelLayout.setVisibility(View.GONE);

        //弹出键盘
        showSoftKeyboard();

    }

    /**
     * 隐藏键盘
     */
    private void hideSoftKeyboard() {
        KeyBoardUtils.closeKeybord(mMyEditText);
        mZhezhaoceng.setVisibility(View.GONE);
    }

    /**
     * 隐藏键盘
     */
    private void showSoftKeyboard() {
        KeyBoardUtils.openKeybord2(mMyEditText);
        mZhezhaoceng.setVisibility(View.VISIBLE);
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
        mPanelHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) mChatContainer.getLayoutParams()).weight = 1;
            }
        }, 200);
    }

    /**
     * 获取本地存储的键盘高度值或者是返回默认值
     */
    private int getSoftKeyboardHeightLocalValue() {
        int value = (int) PanelKeyBoardSPUtils.get(this, KEY_SOFT_KEYBOARD_HEIGHT, SOFT_KEYBOARD_HEIGHT_DEFAULT);
        Log.d(TAG, "键盘高度: " + value);
        return value;
    }



}
