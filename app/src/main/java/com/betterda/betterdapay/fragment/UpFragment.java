package com.betterda.betterdapay.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.TuiGuangActivity;
import com.betterda.betterdapay.activity.UpActivity;
import com.betterda.betterdapay.adapter.MyAdapter;
import com.betterda.betterdapay.util.RxBus;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.HorizontalScrollViewPager;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.Utils.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 升级
 * Created by Administrator on 2016/7/28.
 */
public class UpFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.topbar_up)
    protected NormalTopBar topbarUp;
    @BindView(R.id.iv_up_back)
    public ImageView ivUpBack;
    @BindView(R.id.tv_up_rate)
    protected TextView tvUpRate;
    @BindView(R.id.tv_up_nextrate)
    protected TextView tvUpNextrate;
    @BindView(R.id.linear_up_up)
    protected LinearLayout linearUpUp;
    @BindView(R.id.tv_up_condition)
    protected TextView tvUpCondition;
    @BindView(R.id.tv_up_up)
    protected TextView tvUpUp;
    @BindView(R.id.vp_up)
    protected HorizontalScrollViewPager vpUp;


    protected List<Fragment> list;
    protected Fragment yuanyongFragment, dianzhangFragment, jingliFragment, zongjingliFragment,
            bossFragment;
    private FragmentManager fm;

    private final static String Tag = UpFragment.class.getSimpleName();

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_up, null);

    }

    @Override
    public void initData() {
        super.initData();
        setTopBar();
        setViewpager();


    }



    private void setTopBar() {
        topbarUp.setBackVisibility(false);
        topbarUp.setTitle("升级");
    }

    private void setViewpager() {
        if (null == yuanyongFragment) {
            yuanyongFragment = new YuanGongFragment();

        }
        if (null == dianzhangFragment) {

            dianzhangFragment = new DianZhangFragment();

        }
        if (jingliFragment == null) {

            jingliFragment = new JingLiFragment();
        }
        if (null == zongjingliFragment) {

            zongjingliFragment = new ZongJingLiFragment();
        }
        if (bossFragment == null) {

            bossFragment = new BossFragment();
        }

        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(yuanyongFragment);
        list.add(dianzhangFragment);
        list.add(jingliFragment);
        list.add(zongjingliFragment);
        list.add(bossFragment);
        fm = getChildFragmentManager();
        vpUp.setAdapter(new MyAdapter(fm, list));
        vpUp.setOnPageChangeListener(this);
    }

    @OnClick({R.id.relative_back, R.id.linear_up_up, R.id.tv_up_up})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_back:
                postPre();
                break;
            case R.id.linear_up_up:
                post();
                break;
            case R.id.tv_up_up:
                UtilMethod.startIntent(getmActivity(), UpActivity.class);
                break;
        }
    }

    /**
     * 下一页
     */
    private void post() {
        int nextCurrent = vpUp.getCurrentItem() + 1;
        if (nextCurrent < list.size()) {
            vpUp.setCurrentItem(nextCurrent);
        }
    }

    /**
     * 上一页
     */
    private void postPre() {
        int nextCurrent = vpUp.getCurrentItem() - 1;
        if (nextCurrent >= 0) {
            vpUp.setCurrentItem(nextCurrent);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (list != null) {
            list.clear();
            list = null;
        }
       // RxBus.get().unregister("UpFragment", observable);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position) {
            case 0:
                RxBus.get().post("YuanGongFragment", "");
                break;
            case 1:
                RxBus.get().post("DianZhangFragment", "");
                break;
            case 2:
                RxBus.get().post("JingLiFragment", "");
                break;
            case 3:
                RxBus.get().post("ZongJingLiFragment", "");
                break;
            case 4:
                RxBus.get().post("BossFragment", "");
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
