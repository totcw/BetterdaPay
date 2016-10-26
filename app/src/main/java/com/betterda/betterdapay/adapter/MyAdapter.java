package com.betterda.betterdapay.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.betterda.betterdapay.fragment.YuanGongFragment;

import java.util.List;

/**
 * 使用viewpager的适配器
 * Created by Administrator on 2016/6/1.
 */
public class MyAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public MyAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }



        @Override
        public Fragment getItem(int position) {
            if (null != fragmentList) {

                return fragmentList.get(position);
            } else {
                return  null;
            }
        }

        @Override
        public int getCount() {
            if (null != fragmentList) {

                return fragmentList.size();
            } else {
                return  0;
            }
        }

    /**
     * 继承FragmentStatePagerAdapter和viewpager
     * 使用的时候 要重写这个方法,要不然可能会报空指针异常
     * @param arg0
     * @param arg1
     */
   /* @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        //do nothing here! no call to super.restoreState(arg0, arg1);
    }*/

}
