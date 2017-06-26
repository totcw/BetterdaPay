package com.betterda.betterdapay.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Income;
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
 * 分润明细
 * Created by Administrator on 2017/3/30.
 */

public class WalletDetailActivity extends BaseActivity {

    @BindView(R.id.topbar_walletdetail)
    NormalTopBar mTopbarWalletdetail;
    @BindView(R.id.rv_layout)
    RecyclerView mRvLayout;
    @BindView(R.id.loadpager_layout)
    LoadingPager mLoadpagerLayout;

    private HeaderAndFooterRecyclerViewAdapter mAdapter;

    private List<Income> list,mIncomeList;
    private int page = 1;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.walletdetail);

    }


    @Override
    public void init() {
        super.init();
        mTopbarWalletdetail.setTitle("分润明细");
        list = new ArrayList<>();

        mRvLayout.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mAdapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Income>(getmActivity(),R.layout.item_rv_walletdetail,list) {
            @Override
            public void convert(ViewHolder holder, Income income) {
                if (holder != null && income != null) {
                    holder.setText(R.id.tv_item_walletdetail, income.getAmount()+"元");
                    holder.setText(R.id.tv_item_walletdetail_type, income.getType());
                    holder.setText(R.id.tv_item_walletdetail_time, income.getIncomeTime());
                    holder.setText(R.id.tv_item_walletdetail_account, UtilMethod.transforPhoneNumber(income.getSourceAccount()));
                }
            }

        });

        mRvLayout.addOnScrollListener(new EndlessRecyclerOnScrollListener(getmActivity()) {
            @Override
            public void onLoadNextPage(View view) {
                RecyclerViewStateUtils.next(getmActivity(), mRvLayout, new RecyclerViewStateUtils.nextListener() {
                    @Override
                    public void load() {
                        page++;
                        getData();
                    }
                });

            }

            @Override
            public void show(boolean isShow) {
                //这里是要传当前服务器返回的list
                RecyclerViewStateUtils.show(isShow, mIncomeList, mRvLayout, getmActivity());
            }
        });
        mRvLayout.setAdapter(mAdapter);
        getData();
        mLoadpagerLayout.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
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
        mLoadpagerLayout.setLoadVisable();
        NetworkUtils.isNetWork(this, mLoadpagerLayout, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                mRxManager.add(
                        NetWork.getNetService()
                                .getIncomeList(UtilMethod.getAccout(getmActivity()),page+"", Constants.PAGE_SIZE+"")
                                .compose(NetWork.handleResult(new BaseCallModel<List<Income>>()))
                                .subscribe(new MyObserver<List<Income>>() {
                                    @Override
                                    protected void onSuccess(List<Income> data, String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("分润明细:"+data);
                                        }
                                        if (data != null) {
                                            parserData(data);
                                        }

                                        UtilMethod.judgeData(data,mLoadpagerLayout);
                                    }

                                    @Override
                                    public void onFail(String resultMsg) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            System.out.println("分润明细fail:"+resultMsg);
                                        }
                                        if (mLoadpagerLayout != null) {
                                            mLoadpagerLayout.setErrorVisable();
                                        }
                                    }

                                    @Override
                                    public void onExit() {
                                        if (mLoadpagerLayout != null) {
                                            mLoadpagerLayout.hide();
                                        }
                                    }
                                })
                );
            }
        });
    }

    private void parserData(List<Income> data) {
        mIncomeList = data;
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
