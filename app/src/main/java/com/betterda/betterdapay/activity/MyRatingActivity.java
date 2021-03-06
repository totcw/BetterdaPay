package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.RateData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Rating;
import com.betterda.betterdapay.javabean.TuiGuang;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的费率
 * Created by Administrator on 2016/8/16.
 */
public class MyRatingActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar_myrating)
    NormalTopBar topbarMyrating;
    @BindView(R.id.rv_myrating)
    RecyclerView rvMyrating;
    @BindView(R.id.loadpager_myrating)
    LoadingPager loadingPager;

    private String rate="员工";
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<Rating.RateDetail> list;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_myrating);
    }

    @Override
    public void initListener() {
        super.initListener();
        topbarMyrating.setOnBackListener(this);
        topbarMyrating.setOnActionListener(this);
    }

    @Override
    public void init() {
        setTopBar();
        getRate();
        setRecycleview();
        getData();
    }

    private void getData() {
        loadingPager.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadingPager, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                subscription = NetWork.getNetService(subscription)
                        .getRating(UtilMethod.getAccout(getmActivity()), UtilMethod.getToken(getmActivity()), rate)
                        .compose(NetWork.handleResult(new BaseCallModel<Rating>()))
                        .subscribe(new MyObserver<Rating>() {
                            @Override
                            protected void onSuccess(Rating data, String resultMsg) {

                                if (data != null) {
                                    parser(data);
                                }
                                UtilMethod.judgeData(list,loadingPager);
                            }

                            @Override
                            public void onFail(String resultMsg) {

                                loadingPager.setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                ExitToLogin();
                            }
                        });
            }
        });
    }

    private void parser(Rating data) {

        if (list != null) {
            list.clear();
        }
        for (Rating.RateDetail rateDetail : data.getRateDetail()) {
            list.add(rateDetail);
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void getRate() {
        Intent intent = getIntent();
        if (intent != null) {
            rate = intent.getStringExtra("rate");
        }
    }

    private void setRecycleview() {
        list = new ArrayList<>();
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Rating.RateDetail>(getmActivity(), R.layout.item_recycleview_up, list) {

            @Override
            public void convert(ViewHolder viewHolder, Rating.RateDetail rating) {
                log(rating.getIntoduce());
                if (rating != null) {
                    viewHolder.setText(R.id.tv_item_up_name, rating.getIntoduce());
                    viewHolder.setText(R.id.tv_item_up_rating, rating.getNextrating());
                    viewHolder.setText(R.id.tv_item_up_rating2, rating.getRealrating());
                    viewHolder.setText(R.id.tv_item_up_jiesuan, rating.getNextaccounts());
                    viewHolder.setText(R.id.tv_item_up_jiesuan2, rating.getRealaccounts());
                    viewHolder.setText(R.id.tv_item_up_edu, rating.getNextlimit());
                    viewHolder.setText(R.id.tv_item_up_edu2, rating.getReallime());
                    if (Constants.ZHIFUBAO.equals(rating.getType())) {
                        viewHolder.setImageResource(R.id.iv_item_up, R.mipmap.zhifubao);
                    } else if (Constants.WEIXIN.equals(rating.getType())) {
                        viewHolder.setImageResource(R.id.iv_item_up, R.mipmap.weixin);
                    } else {
                        viewHolder.setImageResource(R.id.iv_item_up, R.mipmap.yinlian);
                    }
                }
            }
        });
        rvMyrating.setAdapter(adapter);
        rvMyrating.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setTopBar() {
        topbarMyrating.setActionText("升级");
        topbarMyrating.setTitle("我的费率");
        topbarMyrating.setActionTextVisibility(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                back();
                break;
            case R.id.bar_action:
                UtilMethod.startIntent(getmActivity(), TuiGuangActivity.class);
                break;
        }
    }
}
