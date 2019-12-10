package com.hjl.emotiondemo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjl.emotiondemo.utils.EmojiKeyboard;
import com.hjl.emotiondemo.utils.PanelKeyboard;
import com.hjl.emotionpicker.EmotionView;

public class EditEmotionActivity extends AppCompatActivity {

    private static final String TAG = "EditEmotionActivity";

    private TextView mTvInputContent;
    private CheckBox mEmotionButton;
    private LinearLayout mRlEditbarBg;
    private EditText mTvEditeText;
    private CheckBox mMoreButton;
    private Button mBarBtnSend;
    private LinearLayout mPanelLayout;
    private LinearLayout mMoreLayout;
    private LinearLayout mEmotionLayout;
    private EmotionView mEmotionView;





    private EmojiKeyboard mEmojiKeyboard;
    private PanelKeyboard mPanelKeyboard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emotion);

        findById();
        //initEmotionView();
        initMoreView();

    }

    private void findById(){
        mTvInputContent = (TextView) findViewById(R.id.tv_input_content);
        mEmotionButton = (CheckBox) findViewById(R.id.emotion_button);
        mRlEditbarBg = (LinearLayout) findViewById(R.id.rl_editbar_bg);
        mTvEditeText = (EditText) findViewById(R.id.tv_editeText);
        mMoreButton = (CheckBox) findViewById(R.id.more_button);
        mBarBtnSend = (Button) findViewById(R.id.bar_btn_send);
        mPanelLayout = (LinearLayout) findViewById(R.id.panel_layout);
        mMoreLayout = (LinearLayout) findViewById(R.id.more_layout);
        mEmotionLayout = (LinearLayout) findViewById(R.id.emotion_layout);
        mEmotionView = (EmotionView) findViewById(R.id.emotion_view);

        mTvInputContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanelKeyboard.hidePanel(false);
               // mEmojiKeyboard.hideEmojiPanel(true);
            }
        });


    }

    private void initEmotionView(){
        //mEmotionView.init(getSupportFragmentManager(),mTvEditeText);

      /*  mEmojiKeyboard = new EmojiKeyboard(this, mTvEditeText, mPanelLayout, mEmotionButton, mTvInputContent);
        mEmojiKeyboard.setEmoticonPanelVisibilityChangeListener(new EmojiKeyboard.OnEmojiPanelVisibilityChangeListener() {
            @Override
            public void onShowEmojiPanel() {
                Log.e(TAG, "onShowEmojiPanel");

                //mMoreKeyboard.hideEmojiPanel(false);
            }

            @Override
            public void onHideEmojiPanel() {
                Log.e(TAG, "onHideEmojiPanel");
            }
        });*/
    }

    private void initMoreView(){

        mEmotionView.init(getSupportFragmentManager(),mTvEditeText);

        mPanelKeyboard = new PanelKeyboard(this, mTvEditeText, mPanelLayout, mTvInputContent);
        mPanelKeyboard.setPanelVisibilityChangeListener(new PanelKeyboard.OnPanelVisibilityChangeListener() {
            @Override
            public void onShowPanel() {
                Log.e(TAG, "onShowPanel");
            }

            @Override
            public void onHidePanel() {
                Log.e(TAG, "onHidePanel");
            }
        });


        //表情按钮
        mEmotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mEmotionButton.isChecked()){
                    mPanelKeyboard.clickShowView();
                }else{
                    mPanelKeyboard.clickHideView();
                }

                mMoreButton.setChecked(false);
                mEmotionLayout.setVisibility(mPanelLayout.isShown()?View.VISIBLE:View.GONE);
                mMoreLayout.setVisibility(!mPanelLayout.isShown()?View.VISIBLE:View.GONE);
            }
        });

        //更多按钮
        mMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMoreButton.isChecked()){
                    mPanelKeyboard.clickShowView();
                }else{
                    mPanelKeyboard.clickHideView();
                }

                mEmotionButton.setChecked(false);

                mMoreLayout.setVisibility(mPanelLayout.isShown()?View.VISIBLE:View.GONE);
                mEmotionLayout.setVisibility(!mPanelLayout.isShown()?View.VISIBLE:View.GONE);
            }
        });

    }


}
