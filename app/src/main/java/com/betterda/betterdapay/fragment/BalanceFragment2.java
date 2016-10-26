package com.betterda.betterdapay.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
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
import com.betterda.mylibrary.LoadingPager;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收款
 * Created by Administrator on 2016/8/9.
 */
public class BalanceFragment2 extends BaseBalanceFragment {
    @BindView(R.id.tv_balbance2_sum)
    TextView tvBalbance2Sum;
    @BindView(R.id.rv_layout)
    RecyclerView rvBalance2;
    @BindView(R.id.loadpager_layout)
    LoadingPager loadpagerLayout;

    private String orderType ="1";

    private String TAG = BalanceFragment2.class.getSimpleName();

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_balance2, null);
    }

    @Override
    public void initData() {
        super.initData();
        setRecycleview(rvBalance2);
        getDataAndPage(1);
    }

    private void getDataAndPage(int p) {
        loadpagerLayout.setLoadVisable();
        getData();
    }

    public void getData() {
        NetworkUtils.isNetWork(getmActivity(), loadpagerLayout, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                subscription= NetWork.getNetService(subscription)
                        .getOrderGet(account, token, orderType, page + "", Constants.PAGE_SIZE + "")
                        .compose(NetWork.handleResult(new BaseCallModel<OrderALL>()))
                        .subscribe(new MyObserver<OrderALL>() {
                            @Override
                            protected void onSuccess(OrderALL data, String resultMsg) {

                                if (data != null) {

                                    setSum(data);
                                    parser(data, resultMsg);
                                }
                                UtilMethod.judgeData(list, loadpagerLayout);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                loadpagerLayout.setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                loadpagerLayout.hide();
                                ExitToLogin();
                            }
                        });
            }
        });


    }

    /**
     * 设置累积收款
     * @param data
     */
    private void setSum(OrderALL data) {
        String sum = data.getHeapCollection();
        if (tvBalbance2Sum != null) {

            tvBalbance2Sum.setText(sum + "元");
        }
    }


}
