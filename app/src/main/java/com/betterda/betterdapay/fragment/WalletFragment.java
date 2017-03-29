package com.betterda.betterdapay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.JieSuanActivity;
import com.betterda.betterdapay.activity.RealNameAuthActivity;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.TuiGuang;
import com.betterda.betterdapay.javabean.Wallet;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 钱包
 * Created by Administrator on 2016/7/28.
 */
public class WalletFragment extends BaseFragment {


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_wallet, null);
    }

    @Override
    public void initData() {
        super.initData();
        setTopBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {

 /*       NetworkUtils.isNetWork(getmActivity(), loadpager_wallet, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                subscription = NetWork.getNetService(subscription)
                        .getWallet(UtilMethod.getAccout(getmActivity()), UtilMethod.getToken(getmActivity()))
                        .compose(NetWork.handleResult(new BaseCallModel<Wallet>()))
                        .subscribe(new MyObserver<Wallet>() {
                            @Override
                            protected void onSuccess(Wallet data, String resultMsg) {
                                if (data != null) {
                                    parser(data);
                                }
                                loadpager_wallet.hide();
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                loadpager_wallet.setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                ExitToLogin();
                            }
                        });
            }
        });*/
    }

    private void parser(Wallet data) {


    }

    private void setTopBar() {

    }

/*    @OnClick({R.id.linear_wallet_jiesuanshoukuan, R.id.linear_wallet_jiesuanfenrun})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_wallet_jiesuanshoukuan:
                jiesuan(true);
                break;
            case R.id.linear_wallet_jiesuanfenrun:
                jiesuan(false);
                break;
        }
    }*/





}
