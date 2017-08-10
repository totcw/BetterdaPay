package com.betterda.betterdapay.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.WithDraw;
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
 * 提现明细
 * Created by Administrator on 2017/3/30.
 */

public class TiXianDetailActivity extends BaseActivity {

    @BindView(R.id.topbar_walletdetail)
    NormalTopBar mTopbarWalletdetail;
    @BindView(R.id.rv_layout)
    RecyclerView mRvLayout;
    @BindView(R.id.loadpager_layout)
    LoadingPager mLoadpagerLayout;

    private List<WithDraw> list,mWithDrawList;
    private HeaderAndFooterRecyclerViewAdapter mAdapter;
    private int page =1;
    private String start = page+"";
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.walletdetail);
    }



    @Override
    public void init() {
        super.init();
        mTopbarWalletdetail.setTitle(Constants.WITHDRAW+"明细");
        list = new ArrayList<>();

        mRvLayout.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mAdapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<WithDraw>(getmActivity(),R.layout.item_rv_withdrawdetail,list) {
            @Override
            public void convert(ViewHolder holder, WithDraw o) {
                if (holder != null && o != null) {
                    holder.setText(R.id.tv_item_withdrawdetail, o.getDrawCash()+"元");
                    holder.setText(R.id.tv_item_withdrawdetail_type, o.getDrawResult());
                    holder.setText(R.id.tv_item_withdrawdetail_time, o.getDrawTime());

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
                RecyclerViewStateUtils.show(isShow, mWithDrawList, mRvLayout, getmActivity());
            }
        });

        mRvLayout.setAdapter(mAdapter);
        mLoadpagerLayout.setLoadVisable();
        getData();
        mLoadpagerLayout.setonErrorClickListener(v -> {
            mLoadpagerLayout.setLoadVisable();
            page = 1;
            start = page+"";
            getData();
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

        NetworkUtils.isNetWork(this, mLoadpagerLayout, () -> mRxManager.add(
                NetWork.getNetService()
                        .getAmountList(UtilMethod.getAccout(getmActivity()),start, Constants.PAGE_SIZE+"",getString(R.string.appCode))
                        .compose(NetWork.handleResult(new BaseCallModel<>()))
                        .subscribe(new MyObserver<List<WithDraw>>() {
                            @Override
                            protected void onSuccess(List<WithDraw> data, String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("结算明细:"+data);
                                }
                                if (data != null) {
                                    parserData(data);
                                }

                                UtilMethod.judgeData(list,mLoadpagerLayout);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("结算明细fail:"+resultMsg);
                                }
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

    private void parserData(List<WithDraw> data) {
        mWithDrawList = data;
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
