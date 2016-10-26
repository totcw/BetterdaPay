package com.betterda.betterdapay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.adapter.MyAdapter;
import com.betterda.betterdapay.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分润
 * Created by Administrator on 2016/8/9.
 */
public class FenRunFragment extends BaseFragment {
    @BindView(R.id.vindicator)
    ViewPagerIndicator vindicator;
    @BindView(R.id.vp_fenrun)
    public ViewPager vpFenrun;
    private Fragment fanhuiFragment, TuiGuangFragment;

    private List<String> mDatas; //viewpager指示器的数据
    private List<Fragment> fragmentList;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_fenrun, null);
    }

    @Override
    public void initData() {
        super.initData();
        setViewpager();
    }

    private void setViewpager() {
        mDatas = new ArrayList<>();
        mDatas.add("返还分润");
        mDatas.add("推广分润");

        fragmentList = new ArrayList<>();
        fanhuiFragment = new FenRunFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt("item", 0);
        fanhuiFragment.setArguments(bundle);
        TuiGuangFragment = new FenRunFragment2();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("item", 1);
        TuiGuangFragment.setArguments(bundle2);
        fragmentList.add(fanhuiFragment);
        fragmentList.add(TuiGuangFragment);

        //设置Tab上的标题
        vindicator.setTabItemTitles(mDatas);
        vpFenrun.setAdapter(new MyAdapter(getChildFragmentManager(), fragmentList));
        //设置关联的ViewPager
        vindicator.setViewPager(vpFenrun, 0);
    }


}
