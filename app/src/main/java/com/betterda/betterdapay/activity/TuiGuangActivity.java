package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.ShapeLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 推广,升级界面
 * Created by Administrator on 2016/8/8.
 */
public class TuiGuangActivity extends BaseActivity {

    @BindView(R.id.topbar_tuiguang)
    NormalTopBar topbarTuiguang;
    @BindView(R.id.relative_tuiguang_my)
    RelativeLayout relativeTuiguangMy;
    @BindView(R.id.tv_tuiguang_rate)
    TextView mTvTuiguangRate;
    @BindView(R.id.tv_tuiguang_money)
    TextView mTvTuiguangMoney;

    private ShapeLoadingDialog dialog;

    private String payUp, rateId;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_tuiguang);
    }

    @Override
    public void init() {
        super.init();
        topbarTuiguang.setTitle("升级");
        Intent intent = getIntent();
        if (intent != null) {
            String rate = intent.getStringExtra("rate");
            mTvTuiguangRate.setText("升级到" + rate);
            payUp = intent.getStringExtra("payUp");
            rateId = intent.getStringExtra("rateId");
            mTvTuiguangMoney.setText(payUp+"元");
        }
        dialog = UtilMethod.createDialog(this, "正在提交...");
    }

    @OnClick({R.id.relative_tuiguang_my, R.id.linear_tuiguang_up, R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_tuiguang_up:
                // TODO 选择付款通道
                chosePayChannel();
                break;
            case R.id.relative_tuiguang_my:
                UtilMethod.startIntent(getmActivity(), TuiguangActivity2.class);
                break;
            case R.id.bar_back:
                back();
                break;
        }
    }

    private void chosePayChannel() {
        UtilMethod.startIntent(getmActivity(),ChoosePayTypePayActivity.class,"money",payUp);
    }

    /**
     * 升级到指定接口
     */
    private void getData() {
        NetworkUtils.isNetWork(this, null, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                UtilMethod.showDialog(getmActivity(), dialog);
                mRxManager.add(
                        NetWork.getNetService().getUpdateToRate(UtilMethod.getAccout(getmActivity()), rateId)
                                .compose(NetWork.handleResult(new BaseCallModel<String>()))
                                .subscribe(new MyObserver<String>() {
                                    @Override
                                    protected void onSuccess(String data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {

                                            System.out.println("升级到指定接口:" + data);
                                        }
                                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {

                                            System.out.println("升级到指定接口fail:" + resultMsg);
                                        }
                                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                                    }

                                    @Override
                                    public void onExit() {
                                        UtilMethod.dissmissDialog(getmActivity(), dialog);
                                    }
                                })
                );
            }
        });
    }


}
