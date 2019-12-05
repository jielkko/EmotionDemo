package com.hjl.emotionpicker;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.hjl.emotionpicker.adapter.EmotionDotAdapter;
import com.hjl.emotionpicker.adapter.EmotionFragmentAdapter;
import com.hjl.emotionpicker.fragment.EmotionFragment;
import com.hjl.emotionpicker.model.EmotionModel;
import com.hjl.emotionpicker.utils.EmotionPicker;

import java.util.ArrayList;
import java.util.List;



public class EmotionView extends LinearLayout {
    private static final String TAG = "CustomerEditText";
    private Context mContext;

    public EmotionView(Context context) {
        super(context);
    }

    public EmotionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public EmotionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    protected List<String> mTitles = new ArrayList<>();
    protected List<Fragment> mFragments = new ArrayList<>();
    protected EmotionFragmentAdapter mAdapter;

    //菜单列表
    private EmotionDotAdapter mDotAdapter;
    public List<String> mDotData = new ArrayList<>();

    private int choosePage = 0;


    private EmotionViewPager mViewPager;
    private RecyclerView mSelectedRecyclerView;




    private List<EmotionModel> mEmotionModel = new ArrayList<>();


    private void init(AttributeSet attrs) {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); //第一个参数是宽,第二个参数是高
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.view_emotion, null);
        view.setLayoutParams(lp);



        mViewPager = (EmotionViewPager) view.findViewById(R.id.view_pager);
        mSelectedRecyclerView = (RecyclerView) view.findViewById(R.id.selected_recycler_view);




        addView(view);

    }
    //FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentManager mFragmentManager;
    private EditText mEditText;

    public void init(FragmentManager mFragmentManager,EditText mEditText){
        this.mFragmentManager = mFragmentManager;
        this.mEditText = mEditText;
        initEmotionView();
        initDotMenuView();
    }



    private void initEmotionView() {
        mTitles.clear();
        mFragments.clear();
        mDotData.clear();


        List<EmotionModel> list = EmotionPicker.getInstance().getEmotionList();
        double d1 = Double.valueOf(list.size() );
        double d2 = Double.valueOf(20);
        Double c = d1/ d2;
        Double pageSize = Math.ceil(c);
        for (int i = 0; i < pageSize; i++) {
            mTitles.add(""+i);

            EmotionFragment fragment = EmotionFragment.newInstance(i);
            fragment.setEditText(mEditText);
            mFragments.add(fragment);
            mDotData.add("round");
        }


        //mAdapter = new EmotionFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mAdapter = new EmotionFragmentAdapter(mFragmentManager, mFragments, mTitles);
        mViewPager.setAdapter(mAdapter);
        //mViewPager.setOffscreenPageLimit(mFragments.size()-1);//设置缓存所有
        mViewPager.setOffscreenPageLimit(0);
        //禁止滑动
        //mViewPager.setScanScroll(false);
        //将tabLayout与viewpager连起来
        //mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("页面=" + position);
                choosePage = position;
                mDotAdapter.setChoose(position);
                mDotAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(choosePage);
    }


    private void initDotMenuView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSelectedRecyclerView.setLayoutManager(layoutManager);

        mDotAdapter = new EmotionDotAdapter(mContext, mDotData);

        mSelectedRecyclerView.setAdapter(mDotAdapter);

        //      调用按钮返回事件回调的方法
        mDotAdapter.buttonSetOnclick(new EmotionDotAdapter.ButtonInterface() {
            @Override
            public void onclick(View view, int position) {

                choosePage = position;
                mViewPager.setCurrentItem(choosePage);
                mDotAdapter.setChoose(position);
                mDotAdapter.notifyDataSetChanged();
            }
        });
        mDotAdapter.setChoose(choosePage);
    }

}
