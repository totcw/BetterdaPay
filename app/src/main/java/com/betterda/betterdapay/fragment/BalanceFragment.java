package com.betterda.betterdapay.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.MingXiActivity;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Order;
import com.betterda.betterdapay.javabean.OrderALL;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.RecyclerViewStateUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.EndlessRecyclerOnScrollListener;
import com.betterda.betterdapay.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.betterdapay.view.LoadingFooter;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 账单
 * Created by Administrator on 2016/7/28.
 */
public class BalanceFragment extends BaseBalanceFragment implements View.OnClickListener {
    @BindView(R.id.topbar_balance)
    NormalTopBar topbarBalance;
    @BindView(R.id.rv_balance)
    RecyclerView rvBalance;
    @BindView(R.id.loadpager_balance)
    LoadingPager loadpager_balance;

    private String TAG = BalanceFragment.class.getSimpleName();
    private String orderType = "0";


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_balance, null);
    }

    @Override
    public void initData() {
        super.initData();
        setTopBar();

        setRecycleview(rvBalance);
        getDataAndPage(1);


    }

    private void setTopBar() {
        topbarBalance.setTitle("账单");
        topbarBalance.setActionText("明细");
        topbarBalance.setBackVisibility(false);
        topbarBalance.setActionTextVisibility(true);
        topbarBalance.setOnActionListener(this);
    }


    private void getDataAndPage(int p) {
        loadpager_balance.setLoadVisable();
        getData();
    }

    public void getData() {
        NetworkUtils.isNetWork(getmActivity(), loadpager_balance, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                subscription=   NetWork.getNetService(subscription)
                        .getOrderGet(account, token, orderType, page + "", Constants.PAGE_SIZE + "")
                        .compose(NetWork.handleResult(new BaseCallModel<OrderALL>()))
                        .subscribe(new MyObserver<OrderALL>() {
                            @Override
                            protected void onSuccess(OrderALL data, String resultMsg) {
                                if (data != null) {

                                    parser(data, resultMsg);
                                }
                                //根据返回的数据显示状态
                                UtilMethod.judgeData(list, loadpager_balance);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    log(resultMsg);
                                }
                                loadpager_balance.setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                loadpager_balance.hide();
                                ExitToLogin();
                            }
                        });
            }
        });


    }




    @Override
    public void onClick(View v) {
        UtilMethod.startIntent(getmActivity(), MingXiActivity.class);
    }
}
