package com.hjl.emotiondemo.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hjl.emotiondemo.common.ContextUtil;

public class PanelKeyboard {

    private Activity activity;

    //文本输入框
    private EditText editText;

    //更多面板
    private View panelView;

    //内容View,即除了表情布局和输入框布局以外的布局
    //用于固定输入框一行的高度以防止跳闪
    private View contentView;


    private InputMethodManager inputMethodManager;



    private static final String Panel_KEYBOARD = "PanelKeyboard";

    private static final String KEY_SOFT_KEYBOARD_HEIGHT = "SoftKeyboardHeight";

    private static final int SOFT_KEYBOARD_HEIGHT_DEFAULT = 654;

    private Handler handler;

    public PanelKeyboard(Activity activity, EditText editText, View panelView, View contentView) {
        init(activity, editText, panelView, contentView);
    }

    private void init(Activity activity, EditText editText, View panelView, View contentView) {
        this.activity = activity;
        this.editText = editText;
        this.panelView = panelView;
        this.contentView = contentView;
        this.editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && PanelKeyboard.this.panelView.isShown()) {
                    lockContentViewHeight();
                    hidePanel(true);
                    unlockContentViewHeight();
                }
                return false;
            }
        });
        this.contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (PanelKeyboard.this.panelView.isShown()) {
                        hidePanel(false);
                    } else if (isSoftKeyboardShown()) {
                        hideSoftKeyboard();
                    }
                }
                return false;
            }
        });

        this.inputMethodManager = (InputMethodManager) this.activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.handler = new Handler();
        init();
    }

    //用于弹出更多面板的View
    public void clickHideView() {
        lockContentViewHeight();
        hidePanel(true);
        unlockContentViewHeight();
    }

    //用于弹出更多面板的View
    public void clickShowView() {
        if (isSoftKeyboardShown()) {
            lockContentViewHeight();
            showPanel();
            unlockContentViewHeight();
        } else {
            showPanel();
        }
    }

    /**
     * 如果之前没有保存过键盘高度值
     * 则在进入Activity时自动打开键盘，并把高度值保存下来
     */
    private void init() {
        if (!PanelKeyBoardSPUtils.contains(ContextUtil.getContext(), KEY_SOFT_KEYBOARD_HEIGHT)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showSoftKeyboard(true);
                }
            }, 200);
        }
    }

    /**
     * 当点击返回键时需要先隐藏更多面板
     */
    public boolean interceptBackPress() {
        if (panelView.isShown()) {
            hidePanel(false);
            return true;
        }
        return false;
    }

    /**
     * 锁定内容View以防止跳闪
     */
    private void lockContentViewHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        layoutParams.height = contentView.getHeight();
        layoutParams.weight = 0;
    }

    /**
     * 释放锁定的内容View
     */
    private void unlockContentViewHeight() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) contentView.getLayoutParams()).weight = 1;
            }
        }, 200);
    }

    /**
     * 获取键盘的高度
     */
    private int getSoftKeyboardHeight() {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //屏幕当前可见高度，不包括状态栏
        int displayHeight = rect.bottom - rect.top;
        //屏幕可用高度
        int availableHeight = ScreenUtils.getAvailableScreenHeight(activity);
        //用于计算键盘高度
        int softInputHeight = availableHeight - displayHeight - ScreenUtils.getStatusBarHeight(activity);
        Log.e("TAG-di", displayHeight + "");
        Log.e("TAG-av", availableHeight + "");
        Log.e("TAG-so", softInputHeight + "");
        if (softInputHeight != 0) {
            // 因为考虑到用户可能会主动调整键盘高度，所以只能是每次获取到键盘高度时都将其存储起来
            PanelKeyBoardSPUtils.put(ContextUtil.getContext(),KEY_SOFT_KEYBOARD_HEIGHT,softInputHeight);
        }

        return softInputHeight;
    }

    /**
     * 获取本地存储的键盘高度值或者是返回默认值
     */
    private int getSoftKeyboardHeightLocalValue() {

        int value = (int) PanelKeyBoardSPUtils.get(ContextUtil.getContext(),KEY_SOFT_KEYBOARD_HEIGHT,SOFT_KEYBOARD_HEIGHT_DEFAULT);
        return  value;
    }

    /**
     * 判断是否显示了键盘
     */
    private boolean isSoftKeyboardShown() {
        return getSoftKeyboardHeight() != 0;
    }

    /**
     * 令编辑框获取焦点并显示键盘
     */
    private void showSoftKeyboard(boolean saveSoftKeyboardHeight) {
        editText.requestFocus();
        inputMethodManager.showSoftInput(editText, 0);
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
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

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
        panelView.getLayoutParams().height = softKeyboardHeight;
        panelView.setVisibility(View.VISIBLE);
        if (panelVisibilityChangeListener != null) {
            panelVisibilityChangeListener.onShowPanel();
        }
    }

    /**
     * 隐藏更多面板，同时指定是否随后开启键盘
     */
    public void hidePanel(boolean showSoftKeyboard) {

        if (panelView.isShown()) {
            panelView.setVisibility(View.GONE);
            if (showSoftKeyboard) {
                showSoftKeyboard(false);
            }
            if (panelVisibilityChangeListener != null) {
                panelVisibilityChangeListener.onHidePanel();
            }
        }
    }

    public interface OnPanelVisibilityChangeListener {

        void onShowPanel();

        void onHidePanel();
    }

    private OnPanelVisibilityChangeListener panelVisibilityChangeListener;

    public void setPanelVisibilityChangeListener(OnPanelVisibilityChangeListener panelVisibilityChangeListener) {
        this.panelVisibilityChangeListener = panelVisibilityChangeListener;
    }

}
