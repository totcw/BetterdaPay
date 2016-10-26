package com.betterda.betterdapay.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.RateData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.Rating;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.RecyclerViewStateUtils;
import com.betterda.betterdapay.util.RxBus;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.EndlessRecyclerOnScrollListener;
import com.betterda.betterdapay.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.betterdapay.view.LoadingFooter;
import com.betterda.mylibrary.LoadingPager;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 员工界面
 * Created by Administrator on 2016/8/1.
 */
public class YuanGongFragment extends BaseUpFragment {
    private final static String TAG = YuanGongFragment.class.getSimpleName();
    @BindView(R.id.rv_yuangong)
    protected RecyclerView rvYuangong;
    @BindView(R.id.loadpager_yuangong)
    protected LoadingPager loadpager_yuangong;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
    }
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_yuangong, null);
    }

    @Override
    public void initData() {
        super.initData();
        item = 0;
        rate = RateData.UP_YUANGONG;
        setRecycleview(rvYuangong,loadpager_yuangong);
        getData(loadpager_yuangong);
        show(item);
        regiseterRxBus(TAG,loadpager_yuangong);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.get().unregister(YuanGongFragment.class.getSimpleName(), observable);
        UtilMethod.unSubscribe(subscribe);
    }


}
