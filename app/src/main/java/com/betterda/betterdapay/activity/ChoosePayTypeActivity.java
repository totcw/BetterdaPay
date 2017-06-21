package com.betterda.betterdapay.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.RateData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.RatingCalculateEntity;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;
import com.betterda.mylibrary.ShapeLoadingDialog;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * 选择支付通道
 * Created by Administrator on 2016/8/4.
 */
public class ChoosePayTypeActivity extends BaseActivity {

    @BindView(R.id.topbar_chose)
    NormalTopBar topbarChose;
    @BindView(R.id.tv_item_balance_money)
    TextView tvItemBalanceMoney;
    @BindView(R.id.tv_item_balance_type)
    TextView tvItemBalanceType;
    @BindView(R.id.rv_choosepaytype)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadpager_choosepaytype)
    LoadingPager mLoadingPager;
    private CommonAdapter<RatingCalculateEntity> mAdapter;

    private  List<RatingCalculateEntity> mList;
    private String money;//支付金额
    private String type = "0";
    private String orderType = "0";
    private String channel;//通道类型
    private String TAG = ChoosePayTypeActivity.class.getSimpleName();


    private ShapeLoadingDialog dialog;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_choosepaytype);

    }

    @Override
    public void init() {
        super.init();
        getInitData();
        topbarChose.setTitle("选择支付通道");
        tvItemBalanceMoney.setText(money + "元");
        dialog = UtilMethod.createDialog(getmActivity(), "正在提交...");
        initRecycleView();
        getDataForRate();
        mLoadingPager.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataForRate();
            }
        });
    }

    private void initRecycleView() {
        mList = new ArrayList<>();
        mAdapter = new CommonAdapter<RatingCalculateEntity>(this,R.layout.rv_item_choosepaytype,mList) {
            @Override
            public void convert(ViewHolder holder, RatingCalculateEntity ratingCalculateEntity) {
                if (holder != null && ratingCalculateEntity != null) {
                    holder.setText(R.id.tv_item_choosepaytype, "单笔额度:" + ratingCalculateEntity.getT0TradeQuota() + "元" +
                            "当天额度:" + ratingCalculateEntity.getT0DayQuota() + "最低手续费:" + ratingCalculateEntity.getT0LeastTradeRate() + "元");
                    channel = "银联";
                    if ("UnionPay".equals(ratingCalculateEntity.getPayWay())) {
                        channel = "银联";
                        holder.setImageResource(R.id.iv_my_information, R.mipmap.yinlian);
                    } else if ("WeChat".equals(ratingCalculateEntity.getPayWay())) {
                        channel = "微信";
                        holder.setImageResource(R.id.iv_my_information, R.mipmap.weixin);
                    } else if ("AliPay".equals(ratingCalculateEntity.getPayWay())) {
                        channel = "支付宝";
                        holder.setImageResource(R.id.iv_my_information, R.mipmap.zhifubao);
                    }
                    holder.setText(R.id.tv_item_choosepaytype_type, channel);

                    holder.setOnClickListener(R.id.relative_choose_zhifubao, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toActivity();
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
    /*        case R.id.relative_choose_zhifubao:
                channel = "支付宝支付";
                getData(0);
                break;
            case R.id.relative_choose_weixin:
                channel = "微信支付";
                getData(1);
                break;
            case R.id.relative_choose_yinlian:
                channel = "银联支付";
               // getData(2);
                toActivity();
               // UtilMethod.startIntent(getmActivity(),MyYinHangKa.class);
                break;*/
            case R.id.bar_back:
                 back();
                break;
        }
    }

    /**
     * 获取各个通道的费率
     */
    private void getDataForRate() {
        mLoadingPager.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), mLoadingPager, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                mRxManager.add(
                        NetWork.getNetService()
                        .getRatingForCalculate(UtilMethod.getAccout(getmActivity()))
                        .compose(NetWork.handleResult(new BaseCallModel<List<RatingCalculateEntity>>()))
                        .subscribe(new MyObserver<List<RatingCalculateEntity>>() {
                            @Override
                            protected void onSuccess(List<RatingCalculateEntity> data, String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("收款:"+data);
                                }
                                if (data != null) {
                                    parserData(data);
                                }
                                if (mLoadingPager != null) {
                                    mLoadingPager.hide();
                                }
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("收款fail:"+resultMsg);
                                }
                                if (mLoadingPager != null) {
                                    mLoadingPager.setErrorVisable();
                                }
                            }

                            @Override
                            public void onExit() {

                            }
                        })
                );
            }
        });
    }

    private void parserData(List<RatingCalculateEntity> data) {
        if (mList != null) {
            mList.addAll(data);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }


    public void getData(final int payType) {
        UtilMethod.showDialog(getmActivity(), dialog);
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
        });


    }

    /**
     * 微信支付
     */
    private void weixinPay() {
        toActivity();
    }

    private void zhifubaoPay() {
        toActivity();
    }
    private void yinlianPay() {
        toActivity();
    }

    private void toActivity() {
        UtilMethod.dissmissDialog(getmActivity(), dialog);
        UtilMethod.startIntent(getmActivity(), CollectionActivity.class, "money", money);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        builder.setCancelable(false);//设置返回键点击不了
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();

    }


}
