package com.betterda.betterdapay.fragment;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.BanLiActivity;
import com.betterda.betterdapay.activity.BianJieDaiKuanActivity;
import com.betterda.betterdapay.activity.CreditpayActivity;
import com.betterda.betterdapay.activity.JieSuanActivity;
import com.betterda.betterdapay.activity.MessageActivity;
import com.betterda.betterdapay.activity.TransactionRecordActivity;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Wallet;
import com.betterda.betterdapay.javabean.WithDrawStatus;
import com.betterda.betterdapay.livingpay.BaseLivingActiivty;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.GradientTextView;
import com.betterda.mylibrary.ShapeLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 钱包
 * Created by Administrator on 2016/7/28.
 */
public class WalletFragment extends BaseFragment {


    @BindView(R.id.gttv_wallet_money)
    GradientTextView mGttvWalletMoney; //提现金额
    @BindView(R.id.iv_wallet_bg)
    ImageView mWalletBg; //图片

    private AlertDialog mAlertDialog;
    private ShapeLoadingDialog mDialog;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_wallet, null);
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGttvWalletMoney != null) {
            mGttvWalletMoney.setText("0");
        }
        getData();
    }


    private void getData() {

        NetworkUtils.isNetWork(getmActivity(), null, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                NetWork.getNetService()
                        .getWallet(UtilMethod.getAccout(getmActivity()))
                        .compose(NetWork.handleResult(new BaseCallModel<Wallet>()))
                        .subscribe(new MyObserver<Wallet>() {
                            @Override
                            protected void onSuccess(Wallet data, String resultMsg) {
                                if (data != null) {
                                    parser(data);
                                }

                            }

                            @Override
                            public void onFail(String resultMsg) {

                            }

                            @Override
                            public void onExit() {

                            }
                        });
            }
        });
    }

    private void parser(Wallet data) {

        if (mGttvWalletMoney != null) {
            mGttvWalletMoney.setText(data.getBalance());
        }
    }


    @OnClick({R.id.iv_wallet_meassage, R.id.btn_wallet_tixian, R.id.relative_shouye_bianjie, R.id.relative_wallet_banli, R.id.relative_shouye_check, R.id.relative_shouye_haidai, R.id.relative_shouye_life})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_wallet_meassage://消息
                UtilMethod.startIntent(getmActivity(), MessageActivity.class);
                break;
            case R.id.btn_wallet_tixian://我要提现
                withdraw();
                break;
            case R.id.relative_wallet_banli://办理信用卡
                UtilMethod.startIntent(getmActivity(), BanLiActivity.class);
                break;
            case R.id.relative_shouye_check://账单查询
                UtilMethod.startIntent(getmActivity(), TransactionRecordActivity.class);
                break;
            case R.id.relative_shouye_haidai://信用卡还贷
                UtilMethod.startIntent(getmActivity(), CreditpayActivity.class);
                break;
            case R.id.relative_shouye_life://生活缴费
                UtilMethod.startIntent(getmActivity(), BaseLivingActiivty.class);
                break;
            case R.id.relative_shouye_bianjie://便捷贷款
                UtilMethod.startIntent(getmActivity(), BianJieDaiKuanActivity.class);
                break;
        }
    }

    /**
     * 提现
     */
    public void withdraw() {

        if (UtilMethod.showNotice(getmActivity())) {
            getWithDraw();
        }
    }

    /**
     * 查询提现状态
     */
    private void getWithDraw() {
        NetworkUtils.isNetWork(getmActivity(), null, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                if (mDialog == null) {
                    mDialog = UtilMethod.createDialog(getmActivity(), "正在加载...");
                }
                UtilMethod.showDialog(getmActivity(), mDialog);
                mRxManager.add(
                        NetWork.getNetService()
                                .getCheckWithdraw(UtilMethod.getAccout(getmActivity()))
                                .compose(NetWork.handleResult(new BaseCallModel<WithDrawStatus>()))
                                .subscribe(new MyObserver<WithDrawStatus>() {
                                    @Override
                                    protected void onSuccess(WithDrawStatus data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("查询结算状态:" + data);
                                        }
                                        UtilMethod.dissmissDialog(getmActivity(), mDialog);
                                        if (data != null) {
                                            if ("10".equals(data.getStatus())) {
                                                createWithDrawDialog();
                                            } else {
                                                if (mGttvWalletMoney != null) {
                                                    UtilMethod.startIntent(getmActivity(), JieSuanActivity.class, "money", mGttvWalletMoney.getText().toString().trim());
                                                }
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("查询结算状态fail:" + resultMsg);
                                        }
                                        UtilMethod.dissmissDialog(getmActivity(), mDialog);
                                    }

                                    @Override
                                    public void onExit() {
                                        UtilMethod.dissmissDialog(getmActivity(), mDialog);
                                    }
                                })
                );
            }
        });
    }

    /**
     * 创建提现的提示对话框
     */
    public void createWithDrawDialog() {
        if (mAlertDialog == null) {
            View view = LayoutInflater.from(getmActivity()).inflate(R.layout.dialog_withdraw, null);
            TextView mTvContent = (TextView) view.findViewById(R.id.tv_dialog_call_content);
            mTvContent.setText("一笔订单正在处理中");
            Button mBtnComfirm = (Button) view.findViewById(R.id.btn_dialog_call_comfrim);
            mBtnComfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UtilMethod.dissmissDialog(getmActivity(), mAlertDialog);
                }
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(getmActivity());
            mAlertDialog = builder.setView(view).create();

        }
        UtilMethod.showDialog(getmActivity(), mAlertDialog);

    }


    @Override
    public void onStop() {
        super.onStop();
        mAlertDialog = null;
    }
}
