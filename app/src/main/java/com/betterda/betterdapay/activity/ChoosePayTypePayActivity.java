package com.betterda.betterdapay.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.betterda.BtPay;
import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.RatingCalculateEntity;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.callback.BtPayCallBack;
import com.betterda.callback.BtResult;
import com.betterda.javabean.BtPayResult;
import com.betterda.javabean.PayCloudReqModel;
import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.ShapeLoadingDialog;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择支付通道(付款)
 * Created by Administrator on 2016/8/4.
 */
public class ChoosePayTypePayActivity extends BaseActivity {

    public static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKqQ8mG2VN2rRi5pF4drOi9pB2kdIAiO6YR7LQGDWQkP2DkAI19apajGxDt3q1m2kmWdytX5dmI8AhxEgK+Ak+qoaf7qNv/6NRQUesnJ8kB7sACzEG79CNwxeZy0jLP2E0RC69r/vyyqcD5PwkIuaMNc5KIJhapl0pPmsMZ+F85QIDAQAB";
    public static final String APP_ID = "47cb95e8badd4521b3bd17da1516d5db";

    @BindView(R.id.topbar_chose)
    NormalTopBar topbarChose;
    @BindView(R.id.rv_choosepaytype)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadpager_choosepaytype)
    LoadingPager mLoadingPager;

    private CommonAdapter<RatingCalculateEntity> mAdapter;
    private  List<RatingCalculateEntity> mList;
    private String money;//支付金额
    private String orderType = "0";
    private String channel;//通道类型
    private String TAG = ChoosePayTypePayActivity.class.getSimpleName();


    private ShapeLoadingDialog dialog;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_choosepaytypepay);

    }

    @Override
    public void init() {
        super.init();
        getInitData();
        topbarChose.setTitle("选择支付通道");
        dialog = UtilMethod.createDialog(getmActivity(), "正在提交...");
        initRecycleView();

    }

    private void initRecycleView() {
        mList = new ArrayList<>();
        mList.add(new RatingCalculateEntity("银联手机控件"));
        mList.add(new RatingCalculateEntity("银联无跳转"));

        mAdapter = new CommonAdapter<RatingCalculateEntity>(this,R.layout.rv_item_choosepaytypepay,mList) {
            @Override
            public void convert(ViewHolder holder, RatingCalculateEntity ratingCalculateEntity) {
                if (holder != null && ratingCalculateEntity != null) {


                    holder.setImageResource(R.id.iv_my_information, R.mipmap.yinlian);
                    holder.setText(R.id.tv_item_choosepaytype_type, ratingCalculateEntity.getPayWay());

                    holder.setOnClickListener(R.id.relative_choose_zhifubao, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getDataForUnionMobilePay();
                        }
                    });
                }
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 获取数据
     */
    private void getInitData() {
        Intent intent = getIntent();
        if (intent != null) {
            money = intent.getStringExtra("money");
        }

    }



    @OnClick({R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                 back();
                break;
        }
    }



    public void getData(final int payType) {
     /*   UtilMethod.showDialog(getmActivity(), dialog);
        NetworkUtils.isNetWork(getmActivity(), topbarChose, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                 NetWork.getNetService()
                        .getOrder(UtilMethod.getAccout(getmActivity()), type, money, orderType, channel)
                        .compose(NetWork.handleResult(new BaseCallModel<String>()))
                        .subscribe(new MyObserver<String>() {
                            @Override
                            protected void onSuccess(String data, String resultMsg) {

                                if (BuildConfig.LOG_DEBUG) {
                                    Log.i(TAG, data + "," + resultMsg);
                                }
                                if (0 == payType) {
                                    zhifubaoPay();
                                } else if (1 == payType) {
                                    weixinPay();
                                } else {
                                    yinlianPay();
                                }

                            }

                            @Override
                            public void onFail(String resultMsg) {
                                showToast(resultMsg);
                                UtilMethod.dissmissDialog(getmActivity(), dialog);
                            }

                            @Override
                            public void onExit() {
                                UtilMethod.dissmissDialog(getmActivity(), dialog);
                                ExitToLogin();
                            }
                        });
            }
        });*/


    }


    /**
     * 获取银联手机控件支付的订单号
     */
    public void getDataForUnionMobilePay() {

    }

    /**
     * 银联手机控件支付
     */
    public void unionMobilePay() {
        PayCloudReqModel payCloudReqModel = new PayCloudReqModel();
        //时间格式:yyyyMMddHHmmss
        String orderId = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String txtAmt = "10";
        payCloudReqModel.setAppid(APP_ID);
        payCloudReqModel.setPublicKey(PUB_KEY);
        payCloudReqModel.setBackUrl("http://www.baidu.com");
        payCloudReqModel.setOrderId(orderId);
        payCloudReqModel.setTxnTime(txnTime);
        payCloudReqModel.setTxnAmt(txtAmt);

        BtPay.getInstance(getmActivity()).requestPay(payCloudReqModel, new BtPayCallBack() {
            @Override
            public void done(BtResult result) {
                showToast(((BtPayResult)result).getResult());
            }
        });
    }


}
