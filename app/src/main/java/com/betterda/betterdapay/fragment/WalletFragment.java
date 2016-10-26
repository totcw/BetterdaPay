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
    @BindView(R.id.topbar_wallet)
    NormalTopBar topbarWallet;
    @BindView(R.id.tv_wallet_shoukuan)
    TextView tvWalletShoukuan;
    @BindView(R.id.tv_wallet_shoukuan2)
    TextView tvWalletShoukuan2;
    @BindView(R.id.linear_wallet_jiesuanshoukuan)
    LinearLayout linearWalletJiesuanshoukuan;
    @BindView(R.id.tv_wallet_fenrun)
    TextView tvWalletFenrun;
    @BindView(R.id.tv_wallet_fenrun2)
    TextView tvWalletFenrun2;
    @BindView(R.id.linear_wallet_jiesuanfenrun)
    LinearLayout linearWalletJiesuanfenrun;
    @BindView(R.id.loadpager_wallet)
    LoadingPager loadpager_wallet;

    private float collection;//可收款结算
    private float fenrun; //可分润结算

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
        if (loadpager_wallet != null) {
            loadpager_wallet.setLoadVisable();
        }
        NetworkUtils.isNetWork(getmActivity(), loadpager_wallet, new NetworkUtils.SetDataInterface() {
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
        });
    }

    private void parser(Wallet data) {

        try {
            collection = Float.parseFloat(data.getCollection());
            fenrun = Float.parseFloat(data.getRating());
        } catch (Exception e) {

        }

        if (tvWalletFenrun != null) {
            tvWalletFenrun.setText(data.getHeapRating() + "元");
        }
        if (tvWalletFenrun2 != null) {
            tvWalletFenrun2.setText(data.getRating() + "元");
        }
        if (tvWalletShoukuan != null) {
            tvWalletShoukuan.setText(data.getHeapCollection() + "元");
        }
        if (tvWalletShoukuan2 != null) {
            tvWalletShoukuan2.setText(data.getCollection() + "元");
        }
    }

    private void setTopBar() {
        topbarWallet.setBackVisibility(false);
        topbarWallet.setTitle("钱包");
    }

    @OnClick({R.id.linear_wallet_jiesuanshoukuan, R.id.linear_wallet_jiesuanfenrun})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_wallet_jiesuanshoukuan:
                jiesuan(true);
                break;
            case R.id.linear_wallet_jiesuanfenrun:
                jiesuan(false);
                break;
        }
    }


    /**
     * 结算
     */
    private void jiesuan(boolean isCollection) {
        boolean aBoolean = CacheUtils.getBoolean(getmActivity(), Constants.Cache.AUTH, false);
        if (!aBoolean) {//没有实名认证
            showDialog();


        } else {
            Intent intent = new Intent(getmActivity(), JieSuanActivity.class);
            float money = isCollection?collection:fenrun;
            intent.putExtra("money",money);
            startActivity(intent);
        }

    }

    @Override
    public void comfirmDialog() {
        super.comfirmDialog();
        UtilMethod.startIntent(getmActivity(), RealNameAuthActivity.class);
    }

    @Override
    public String getDialogTitle() {
        return "温馨提示";
    }

    @Override
    public String getDialogMessage() {
        return "立即进行实名认证?";
    }
}
