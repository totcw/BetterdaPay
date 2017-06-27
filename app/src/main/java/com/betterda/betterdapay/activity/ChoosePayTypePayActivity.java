package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.BtPay;
import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.CreateOrderEntity;
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

import java.util.ArrayList;
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
    private List<RatingCalculateEntity> mList;
    private String payUp;//支付金额
    private String rankId;//升级到指定等级
    private int mMoney;//单位为分

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
        dialog = UtilMethod.createDialog(getmActivity(), "正在加载...");
        initRecycleView();

    }

    private void initRecycleView() {
        mList = new ArrayList<>();
        mList.add(new RatingCalculateEntity("银联手机控件支付"));
        mList.add(new RatingCalculateEntity("银联无跳转支付"));

        mAdapter = new CommonAdapter<RatingCalculateEntity>(this, R.layout.rv_item_choosepaytypepay, mList) {
            @Override
            public void convert(final ViewHolder holder, RatingCalculateEntity ratingCalculateEntity) {
                if (holder != null && ratingCalculateEntity != null) {


                    holder.setImageResource(R.id.iv_my_information, R.mipmap.yinlian);
                    holder.setText(R.id.tv_item_choosepaytype_type, ratingCalculateEntity.getPayWay());

                    holder.setOnClickListener(R.id.relative_choose_zhifubao, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.getAdapterPosition() == 0) {

                                getDataForUnionMobilePay();
                            } else {
                                //TODO 无跳转
                            }
                        }
                    });
                }
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 获取数据
     */
    private void getInitData() {
        Intent intent = getIntent();
        if (intent != null) {
            payUp = intent.getStringExtra("money");
            rankId = intent.getStringExtra("rateId");
        }
        if (TextUtils.isEmpty(payUp)) {
            payUp = "0";
        }
        try {
            mMoney = (int) (Float.valueOf(payUp) * 100);
        } catch (Exception e) {

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


    /**
     * 获取银联手机控件支付的订单号
     */
    public void getDataForUnionMobilePay() {
        NetworkUtils.isNetWork(getmActivity(), null, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                UtilMethod.showDialog(getmActivity(), dialog);
                mRxManager.add(
                        NetWork.getNetService()
                                .getOrder(UtilMethod.getAccout(getmActivity()), mMoney + "", rankId, "升级付款")
                                .compose(NetWork.handleResult(new BaseCallModel<CreateOrderEntity>()))
                                .subscribe(new MyObserver<CreateOrderEntity>() {
                                    @Override
                                    protected void onSuccess(CreateOrderEntity data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("手机支付控件:" + data);
                                        }
                                        if (data != null) {
                                            unionMobilePay(data);
                                        } else {
                                            UtilMethod.dissmissDialog(getmActivity(), dialog);
                                            showToast("获取支付订单失败");
                                        }
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        showToast("获取支付订单失败");
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

    /**
     * 银联手机控件支付
     */
    public void unionMobilePay(CreateOrderEntity data) {

        PayCloudReqModel payCloudReqModel = new PayCloudReqModel();
        payCloudReqModel.setAppid(APP_ID);
        payCloudReqModel.setPublicKey(PUB_KEY);
        payCloudReqModel.setBackUrl(data.getNotifyUrl());
        payCloudReqModel.setOrderId(data.getOrderId());
        payCloudReqModel.setTxnTime(data.getTxnTime());
        payCloudReqModel.setTxnAmt(mMoney + "");

        BtPay.getInstance(getmActivity()).requestPay(payCloudReqModel, new BtPayCallBack() {
            @Override
            public void done(BtResult result) {
                UtilMethod.dissmissDialog(getmActivity(), dialog);
                BtPayResult payResult = (BtPayResult) result;
                if (payResult != null) {
                    if (BtPayResult.RESULT_SUCCESS.equals(payResult.getResult())) {
                        finish();
                    } else if (BtPayResult.RESULT_CANCEL.equals(payResult.getResult())) {
                        showToast("取消支付");
                    } else  {
                        showToast("支付失败");
                    }
                }
                BtPay.clear();
            }
        });
    }


}
