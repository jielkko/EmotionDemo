package com.hjl.emotionpicker.utils;


import android.support.v4.util.ArrayMap;

import com.hjl.emotionpicker.R;
import com.hjl.emotionpicker.model.EmotionModel;

import java.util.ArrayList;
import java.util.List;

public class EmotionPicker {
    public static final String TAG = EmotionPicker.class.getSimpleName();
    /**
     * key-表情文字;
     * value-表情图片资源
     */
    public static ArrayMap<String, Integer> EMPTY_MAP;
    public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;

    public static List<EmotionModel> lists = new ArrayList<>();

    private static EmotionPicker mInstance;

    private EmotionPicker() {
        initData();
    }
    public static EmotionPicker getInstance() {
        if (mInstance == null) {
            synchronized (EmotionPicker.class) {
                if (mInstance == null) {
                    mInstance = new EmotionPicker();
                }
            }
        }
        return mInstance;
    }
    /**
     * 根据名称获取当前表情图标R值
     *
     * @param
     * @param imgName 名称
     * @return
     */
    public static int getImgByName(String imgName) {
        Integer integer = null;
        integer = EMOTION_CLASSIC_MAP.get(imgName);
        return integer == null ? -1 : integer;
    }


    public static List<EmotionModel> getEmotionList() {

        return lists;
    }

    public static void setEmotionList(List<EmotionModel> list) {
        lists.clear();
        lists.addAll(list);
        EMOTION_CLASSIC_MAP.clear();
        for (EmotionModel item : lists) {
            EMOTION_CLASSIC_MAP.put(item.getName(), item.getIcon());
        }
    }

    private void initData() {
        EMOTION_CLASSIC_MAP = new ArrayMap<>();

        for (int i = 0; i < 50; i++) {
            lists.add(new EmotionModel("[测试" + i + "]", R.drawable.d_test));
        }


        for (EmotionModel item : lists) {
            EMOTION_CLASSIC_MAP.put(item.getName(), item.getIcon());
        }
    }



}
