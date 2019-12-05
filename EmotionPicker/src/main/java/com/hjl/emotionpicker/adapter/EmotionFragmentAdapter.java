package com.hjl.emotionpicker.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;



public class EmotionFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list_fragment;                         //fragment列表
    private List<String> list_Title;                              //tab名的列表

    public EmotionFragmentAdapter(FragmentManager fm, List<Fragment>list_fragment, List<String> list_Title) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
    }

    @Override
    public Fragment getItem(int i) {
        return list_fragment.get(i);
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }



    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }


/*    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager basePager = mList.get(position);

        //basePager.initData();//加载数据

        View view = basePager.mRootView;//获取当前页面的布局
        container.addView(view);
        return view;
    }*/

    /**

     * 此方法是给tablayout中的tab赋值的，就是显示名称

     * @param position

     * @return

     */

    @Override

    public CharSequence getPageTitle(int position) {

        return list_Title.get(position % list_Title.size());

    }

}

