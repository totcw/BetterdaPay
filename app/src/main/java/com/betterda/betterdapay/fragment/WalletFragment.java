package com.betterda.betterdapay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.BianJieDaiKuanActivity;
import com.betterda.betterdapay.javabean.Wallet;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.GradientTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 钱包
 * Created by Administrator on 2016/7/28.
 */
public class WalletFragment extends BaseFragment {


    @BindView(R.id.gttv_wallet_money)
    GradientTextView mGttvWalletMoney;
    @BindView(R.id.relative_shouye_bianjie)
    RelativeLayout mRelativeShouyeBianjie;

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



    @OnClick({R.id.iv_wallet_meassage, R.id.btn_wallet_tixian,R.id.relative_shouye_bianjie, R.id.relative_wallet_banli, R.id.relative_shouye_check, R.id.relative_shouye_haidai, R.id.relative_shouye_life})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_wallet_meassage:
                break;
            case R.id.btn_wallet_tixian:
                break;
            case R.id.relative_wallet_banli:
                break;
            case R.id.relative_shouye_check:
                break;
            case R.id.relative_shouye_haidai:
                break;
            case R.id.relative_shouye_life:
                break;
            case R.id.relative_shouye_bianjie:
                UtilMethod.startIntent(getmActivity(), BianJieDaiKuanActivity.class);
                break;
        }
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
