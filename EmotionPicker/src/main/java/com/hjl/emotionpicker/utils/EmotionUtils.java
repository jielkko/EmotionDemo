
package com.hjl.emotionpicker.utils;



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
