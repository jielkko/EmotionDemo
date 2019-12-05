
package com.hjl.emotionpicker.utils;

import android.support.v4.util.ArrayMap;
import android.util.Log;


import com.hjl.emotionpicker.R;
import com.hjl.emotionpicker.model.EmotionModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SiberiaDante
 * Describe: 表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 * Time: 2017/6/26
 * Email: 994537867@qq.com
 * GitHub: https://github.com/SiberiaDante
 * 博客园： http://www.cnblogs.com/shen-hua/
 */
public class EmotionUtils {

    /**
     * 根据名称获取当前表情图标R值
     *
     * @param
     * @param imgName     名称
     * @return
     */
    public static int getImgByName(String imgName) {
        Integer integer = null;
        integer = EmotionPicker.getInstance().getImgByName(imgName);
        return integer == null ? -1 : integer;
    }


}
