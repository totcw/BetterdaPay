package com.betterda.betterdapay.activity;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.TransactionRecord;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.RecyclerViewStateUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.EndlessRecyclerOnScrollListener;
import com.betterda.betterdapay.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 交易记录
 * Created by Administrator on 2017/3/30.
 */

public class TransactionRecordActivity extends BaseActivity {

    public final static String PAYTMENT_PAY = "20";//付款
    public final static String PAYTMENT_GET = "10";//收款

    public final static String PAYSTATUS_NO = "N";//待付款
    public final static String PAYSTATUS_SUCCESS = "Y";//交易成功
    public final static String PAYSTATUS_FAIL = "C";//交易失败




    @BindView(R.id.topbar_tranastionrecord)
    NormalTopBar mTopbarTranastionrecord;
    @BindView(R.id.rv_layout)
    RecyclerView mRvLayout;
    @BindView(R.id.loadpager_layout)
    LoadingPager mLoadpagerLayout;
    private HeaderAndFooterRecyclerViewAdapter mAdapter;

    private List<TransactionRecord> list,mTransactionRecordList;
    private int page = 1;
    private String start = page + "";
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_transactionrecord);
     
    }

    @Override
    public void init() {
        super.init();
        mTopbarTranastionrecord.setTitle("交易记录");
        initRecycleview();
        mLoadpagerLayout.setonErrorClickListener(v -> {
            mLoadpagerLayout.setLoadVisable();
            page = 1;
            start = page + "";
            getData();
        });
        mLoadpagerLayout.setLoadVisable();
        getData();
    }

    private void initRecycleview() {
        list = new ArrayList<>();
        mRvLayout.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mAdapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<TransactionRecord>(getmActivity(),R.layout.item_recycleview_tranastionreord,list) {
            @Override
            public void convert(ViewHolder holder, TransactionRecord transactionRecord) {
                if (transactionRecord != null && holder != null) {
                    if (PAYTMENT_GET .equals(transactionRecord.getPaymentType())) {

                        holder.setText(R.id.tv_mingxi_money,"+"+ transactionRecord.getTxnAmt() + "元");
                        holder.setTextColor(R.id.tv_mingxi_time, Color.RED);
                    } else if (PAYTMENT_PAY.equals(transactionRecord.getPaymentType())) {

                        holder.setText(R.id.tv_mingxi_money,"-"+ transactionRecord.getTxnAmt() + "元");
                        holder.setTextColor(R.id.tv_mingxi_time, Color.BLACK);
                    }

                    if (PAYSTATUS_SUCCESS.equals(transactionRecord.getPayStatus())) {
                        holder.setText(R.id.tv_mingxi_status,"交易成功");
                    } else if (PAYSTATUS_FAIL.equals(transactionRecord.getPayStatus())) {
                        holder.setText(R.id.tv_mingxi_status,"交易失败");
                    } else {
                        holder.setText(R.id.tv_mingxi_status,"待支付");
                    }


                    holder.setText(R.id.tv_mingxi_type, transactionRecord.getPlatMerId());
                    holder.setText(R.id.tv_mingxi_order, "订单号:"+transactionRecord.getOrderId());
                    holder.setText(R.id.tv_mingxi_time, transactionRecord.getTxnTime());


                }
            }


        });

        mRvLayout.addOnScrollListener(new EndlessRecyclerOnScrollListener(getmActivity()) {
            @Override
            public void onLoadNextPage(View view) {
                RecyclerViewStateUtils.next(getmActivity(), mRvLayout, () -> {
                    page++;
                    start = UtilMethod.getStart(page);
                    getData();
                });

            }

            @Override
            public void show(boolean isShow) {
                //这里是要传当前服务器返回的list
                RecyclerViewStateUtils.show(isShow, mTransactionRecordList, mRvLayout, getmActivity());
            }
        });

        mRvLayout.setAdapter(mAdapter);
    }



    @OnClick({R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;

        }
    }

    private void getData() {

        NetworkUtils.isNetWork(getmActivity(), mLoadpagerLayout, () -> mRxManager.add(
                NetWork.getNetService()
                        .getOrders(UtilMethod.getAccout(getmActivity()), null, null, start, Constants.PAGE_SIZE + "",getString(R.string.appCode))
                        .compose(NetWork.handleResult(new BaseCallModel<>()))
                        .subscribe(new MyObserver<List<TransactionRecord>>() {
                            @Override
                            protected void onSuccess(List<TransactionRecord> data, String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("账单:"+data);
                                }
                                if (data != null) {
                                    parserData(data);
                                }
                                UtilMethod.judgeData(list,mLoadpagerLayout);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (mLoadpagerLayout != null) {
                                    mLoadpagerLayout.setErrorVisable();
                                }
                            }

                            @Override
                            public void onExit(String resultMsg) {
                                ExitToLogin(resultMsg);
                            }
                        })
        ));
    }

    private void parserData(List<TransactionRecord> data) {
        mTransactionRecordList = data;
        if (list != null) {
            if (page == 1) {
                list.clear();
            }
            list.addAll(data);
        }

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
