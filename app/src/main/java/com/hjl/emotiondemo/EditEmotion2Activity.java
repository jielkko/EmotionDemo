package com.hjl.emotiondemo;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjl.emotiondemo.utils.KeyBoardUtils;
import com.hjl.emotiondemo.utils.PanelKeyboard;
import com.hjl.emotionpicker.EmotionAllView;
import com.lai.library.ButtonStyle;

public class EditEmotion2Activity extends AppCompatActivity {

    private static final String TAG = "EditEmotion2Activity";

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






    private PanelKeyboard mPanelKeyboard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emotion2);

        findById();
        //initEmotionView();
        initMoreView();

    }

    private void findById(){
        mChatContainer = (LinearLayout) findViewById(R.id.chat_container);
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
        mMoreLayout = (LinearLayout) findViewById(R.id.more_layout);
        mEmotionLayout = (LinearLayout) findViewById(R.id.emotion_layout);
        mEmotionView = (EmotionAllView) findViewById(R.id.emotion_view);

        mRefreshLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanelKeyboard.hidePanel(false);

                mVoiceButton.setChecked(false);
                mMoreButton.setChecked(false);
                mEmotionButton.setChecked(false);
            }
        });

        mMyEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanelKeyboard.hidePanel(false);

                mVoiceButton.setChecked(false);
                mMoreButton.setChecked(false);
                mEmotionButton.setChecked(false);
            }
        });

        mMyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = mMyEditText.getText().toString();
                if ("".equals(content)) {
                    mMoreButton.setVisibility(View.VISIBLE);
                    mSendBtn.setVisibility(View.GONE);
                } else {
                    Log.d(TAG, "afterTextChanged: "+content);
                    mMoreButton.setVisibility(View.GONE);
                    mSendBtn.setVisibility(View.VISIBLE);
                }
            }
        });


        mVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mVoiceButton.isChecked()){

                    //显示语音
                    mPanelKeyboard.clickHideView2();

                    mMoreButton.setVisibility(View.VISIBLE);
                    mSendBtn.setVisibility(View.GONE);

                    mEmotionButton.setChecked(false);
                    mMoreButton.setChecked(false);

                    mEmotionLayout.setVisibility(View.GONE);
                    mMoreLayout.setVisibility(View.GONE);

                    mRecorderButton.setVisibility(View.VISIBLE);
                    mMyEditText.setVisibility(View.GONE);


                    KeyBoardUtils.closeKeybord(mMyEditText);
                }else{
                    mPanelKeyboard.clickHideView2();


                    mEmotionButton.setChecked(false);
                    mMoreButton.setChecked(false);

                    mEmotionLayout.setVisibility(mPanelLayout.isShown()?View.VISIBLE:View.GONE);
                    mMoreLayout.setVisibility(!mPanelLayout.isShown()?View.VISIBLE:View.GONE);

                    mRecorderButton.setVisibility(View.GONE);
                    mMyEditText.setVisibility(View.VISIBLE);

                    KeyBoardUtils.openKeybord2(mMyEditText);
                    String content = mMyEditText.getText().toString();
                    if ("".equals(content)) {
                        mMoreButton.setVisibility(View.VISIBLE);
                        mSendBtn.setVisibility(View.GONE);
                    } else {
                        Log.d(TAG, "afterTextChanged: "+content);
                        mMoreButton.setVisibility(View.GONE);
                        mSendBtn.setVisibility(View.VISIBLE);
                    }
                }



            }
        });


    }




    private void initMoreView(){

        mEmotionView.init(mMyEditText);

        mPanelKeyboard = new PanelKeyboard(this, mMyEditText, mPanelLayout, mChatContainer);
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

                mRecorderButton.setVisibility(View.GONE);
                mMyEditText.setVisibility(View.VISIBLE);

                mVoiceButton.setChecked(false);
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

                mRecorderButton.setVisibility(View.GONE);
                mMyEditText.setVisibility(View.VISIBLE);

                mVoiceButton.setChecked(false);
                mEmotionButton.setChecked(false);

                mMoreLayout.setVisibility(mPanelLayout.isShown()?View.VISIBLE:View.GONE);
                mEmotionLayout.setVisibility(!mPanelLayout.isShown()?View.VISIBLE:View.GONE);
            }
        });

    }


}
