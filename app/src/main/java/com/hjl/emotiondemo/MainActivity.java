package com.hjl.emotiondemo;


import com.hjl.emotiondemo.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.hjl.emotionpicker.EmotionView;


public class MainActivity extends AppCompatActivity {
    private EditText mTvEditeText;
    private EmotionView mEmotionView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTvEditeText = (EditText) findViewById(R.id.tv_editeText);
        mEmotionView = (EmotionView) findViewById(R.id.emotion_view);

        initEmotion();
    }

    private void initEmotion(){

        mEmotionView.init(getSupportFragmentManager(),mTvEditeText);
    }
}
