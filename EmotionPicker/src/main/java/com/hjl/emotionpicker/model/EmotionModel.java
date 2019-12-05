package com.hjl.emotionpicker.model;

public class EmotionModel {
    private String name;
    private int icon;

    public EmotionModel(){

    }

    public EmotionModel(String name,int icon){
       this.name = name;
        this.icon = icon;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
