package com.hjl.emotiondemo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.hjl.emotiondemo.common.ContextUtil;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * <pre>
 *  author : wyz
 *  e_mail : xxx@xx
 *  time  : 2017/06/${DYA}
 *  desc :
 *  version: 1.0
 * </pre>
 */

public class KeyBoardUtils {
    private static final String TAG = "KeyBoardUtils";


    /**
     * 打开键盘
     **/
    public static void openKeybord2(EditText v) {

        //获取焦点
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();//获取焦点 光标出现

        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

    }

    /**
     * 打开键盘
     **/
    public static void openKeybord(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

    }

    /**
     * 2. * 关闭软键盘
     * 3. *
     * 4. * @param v
     * 5.
     */
    public static void closeKeybord(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }


    /**
     * 设置键盘显示与隐藏
     *
     * @param context
     * @param view
     * @param visible
     */
    public static boolean setIMM(Context context, View view, boolean visible) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (visible) {
            return imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } else {
            return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }


    /**
     * des:隐藏软键盘,这种方式参数为activity
     * 但没有失去焦点
     *
     * @param activity
     */
    public static void hideInputForce(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null)
            return;

        ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 打开软键盘
     * 魅族可能会有问题
     *
     * @param mEditText
     * @param mContext
     */
    public static void showInput(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


    }


    /**
     * 获取软件盘的高度
     *
     * @return
     */
    public static int getSupportSoftInputHeight(Activity mActivity) {
        Rect r = new Rect();
        /**
         * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
         * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
         */
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕的高度
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - r.bottom;

        if (softInputHeight == 0) {
            softInputHeight = getKeyBoardHeight();

            return softInputHeight;
        }

        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight(mActivity);
        }

        if (softInputHeight < 0) {
            Log.d(TAG, "getSupportSoftInputHeight: EmotionKeyboard--Warning: value of softInputHeight is below zero!");
        }
        //存一份到本地
        if (softInputHeight > 0) {
            PanelKeyBoardSPUtils.put(ContextUtil.getContext(), "keyboardheight", softInputHeight);
        }
        return softInputHeight;
    }


    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int getSoftButtonsBarHeight(Activity mActivity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 获取软键盘高度，由于第一次直接弹出表情时会出现小问题，787是一个均值，作为临时解决方案
     *
     * @return
     */
    public static int getKeyBoardHeight() {
        int softInputHeight = (int) PanelKeyBoardSPUtils.get(ContextUtil.getContext(), "keyboardheight", 600);
        Log.d(TAG, "键盘高度: " + softInputHeight);
        return softInputHeight;

    }

    /**
     * 获取软键盘高度，由于第一次直接弹出表情时会出现小问题，787是一个均值，作为临时解决方案
     *
     * @return
     */
    public static void setKeyBoardHeight(Activity mActivity) {
        getSupportSoftInputHeight(mActivity);

    }

}
