package com.betterda.betterdapay.fragment;

import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.JieSuanActivity;
import com.betterda.betterdapay.activity.MessageActivity;
import com.betterda.betterdapay.activity.TransactionRecordActivity;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Wallet;
import com.betterda.betterdapay.javabean.WithDrawStatus;
import com.betterda.betterdapay.receiver.JpushReceiver;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.ScrollWidget;
import com.betterda.mylibrary.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 钱包
 * Created by Administrator on 2016/7/28.
 */
public class WalletFragment extends BaseFragment {


    @BindView(R.id.gttv_wallet_money)
    AppCompatEditText mGttvWalletMoney; //提现金额
    @BindView(R.id.iv_shouye_message)
    ImageView mIvMessage; //图片
    @BindView(R.id.scrollwidget)
    ScrollWidget mScrollWidget;


    private AlertDialog mAlertDialog;
    private ShapeLoadingDialog mDialog;

    private List<String> mList;


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_wallet, null);
    }

    @Override
    public void initData() {
        super.initData();

        initRxBus();
        startScroll();

    }



    @Override
    public void onStart() {
        super.onStart();
        if (mGttvWalletMoney != null) {
            mGttvWalletMoney.setText("0");
        }
        judgeMessage();
        getData();

        //设置5.0以下 兼容着色 tint

        ColorStateList list = ContextCompat.getColorStateList(getmActivity(), R.color.tint_color);
        mGttvWalletMoney.setSupportBackgroundTintList(list);
    }


    /**
     * 判断是否是有新消息
     */
    private void judgeMessage() {
        String account = CacheUtils.getString(getmActivity(), Constants.Cache.ACCOUNT, "");
        boolean messsage = CacheUtils.getBoolean(getmActivity(), account + Constants.Cache.MESSAGE, false);
        if (mIvMessage != null) {
            mIvMessage.setSelected(messsage);
        }
    }

    /**
     * 设置rxbus
     */
    private void initRxBus() {
        mRxManager.on(JpushReceiver.class.getSimpleName(), new Action1<Boolean>() {
            @Override
            public void call(Boolean s) {
                //改变消息是样式
                if (mIvMessage != null) {
                    if (s) {
                        mIvMessage.setSelected(true);
                    } else {
                        mIvMessage.setSelected(false);
                    }

                }
            }
        });
    }


    private void getData() {

        NetworkUtils.isNetWork(getmActivity(), null, () -> NetWork.getNetService()
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
                    public void onExit(String resultMsg) {
                        ExitToLogin(resultMsg);
                    }
                }));
    }

    private void parser(Wallet data) {

        if (mGttvWalletMoney != null) {
            mGttvWalletMoney.setText(data.getBalance());
        }
    }


    @OnClick({R.id.relative_shouye_message, R.id.gttv_wallet_money, R.id.relative_shouye_bianjie, R.id.relative_wallet_banli, R.id.relative_shouye_check, R.id.relative_shouye_haidai, R.id.relative_shouye_life})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_shouye_message://消息
                UtilMethod.startIntent(getmActivity(), MessageActivity.class);
                break;
            case R.id.gttv_wallet_money://我要提现
                withdraw();
                break;
            case R.id.relative_wallet_banli://办理信用卡
                showToast("正在开发中");
                // UtilMethod.startIntent(getmActivity(), BanLiActivity.class);
                break;
            case R.id.relative_shouye_check://账单查询
                UtilMethod.startIntent(getmActivity(), TransactionRecordActivity.class);
                break;
            case R.id.relative_shouye_haidai://信用卡还贷
                showToast("正在开发中");
                // UtilMethod.startIntent(getmActivity(), CreditpayActivity.class);
                break;
            case R.id.relative_shouye_life://生活缴费
                showToast("正在开发中");
                // UtilMethod.startIntent(getmActivity(), BaseLivingActiivty.class);
                break;
            case R.id.relative_shouye_bianjie://便捷贷款
                showToast("正在开发中");
                // UtilMethod.startIntent(getmActivity(), BianJieDaiKuanActivity.class);
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
                                            createWithDrawDialog(data.getDisburseTime());

                                        }

                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("查询结算状态fail:" + resultMsg);
                                        }
                                        if (mGttvWalletMoney != null) {
                                            UtilMethod.startIntent(getmActivity(), JieSuanActivity.class, "money", mGttvWalletMoney.getText().toString().trim());
                                        }
                                        UtilMethod.dissmissDialog(getmActivity(), mDialog);
                                    }

                                    @Override
                                    public void onExit(String resultMsg) {
                                        UtilMethod.dissmissDialog(getmActivity(), mDialog);
                                        ExitToLogin(resultMsg);
                                    }
                                })
                );
            }
        });
    }

    /**
     * 创建提现的提示对话框
     */
    public void createWithDrawDialog(String time) {
        if (mAlertDialog == null) {
            View view = LayoutInflater.from(getmActivity()).inflate(R.layout.dialog_withdraw, null);
            TextView mTvContent = (TextView) view.findViewById(R.id.tv_dialog_call_content);
            mTvContent.setText("您于" + time + "提交了一笔提现,请等待处理");
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

    /**
     * 快报开始滑动
     */
    private void startScroll() {
        mList = new ArrayList<>();
        for ( int i=0;i<30;i++) {
            mList.add("用户" + i + "获取到" + i + "分润");
        }
        mScrollWidget.setData(mList);
        mScrollWidget.startRunning();


    }


    @Override
    public void onStop() {
        super.onStop();
        mAlertDialog = null;

    }
}
