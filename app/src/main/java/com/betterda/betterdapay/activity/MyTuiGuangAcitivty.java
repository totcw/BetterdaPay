package com.betterda.betterdapay.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.RateData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.TuiGuang;
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

/**
 * 我的推广
 * Created by Administrator on 2016/8/17.
 */
public class MyTuiGuangAcitivty extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar_mytuiguang)
    NormalTopBar topbarMytuiguang;
    @BindView(R.id.rv_layout)
    RecyclerView rvLayout;
    @BindView(R.id.loadpager_layout)
    LoadingPager loadpagerLayout;

    private List<TuiGuang> list, tuiGuangList;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private int page = 1;
    @Override
    public void initView() {
        setContentView(R.layout.activity_mytuiguang);
    }

    @Override
    public void init() {
        topbarMytuiguang.setTitle("我的推广");
        setRecycleview();
        getData();
    }

    private void getData() {
        loadpagerLayout.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadpagerLayout, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                subscription = NetWork.getNetService(subscription)
                        .getSub(UtilMethod.getAccout(getmActivity()), UtilMethod.getToken(getmActivity()), "1", page + "", Constants.PAGE_SIZE + "")
                        .compose(NetWork.handleResult(new BaseCallModel<List<TuiGuang>>()))
                        .subscribe(new MyObserver<List<TuiGuang>>() {
                            @Override
                            protected void onSuccess(List<TuiGuang> data, String resultMsg) {
                                if (data != null) {
                                    parser(data);
                                }
                                UtilMethod.judgeData(list,loadpagerLayout);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                loadpagerLayout.setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                ExitToLogin();
                            }
                        });
            }
        });
    }

    private void parser(List<TuiGuang> data) {
        tuiGuangList = data;
        if (page == 1) {
            if (list != null) {
                list.clear();
            }
        }
        for (TuiGuang tuiGuang : tuiGuangList) {
            if (list != null) {
                list.add(tuiGuang);
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }

    private void setRecycleview() {
        list = new ArrayList<>();
        tuiGuangList = new ArrayList<>();

        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<TuiGuang>(getmActivity(), R.layout.item_recycleview_mytuiguang, list) {

            @Override
            public void convert(ViewHolder viewHolder, TuiGuang tuiGuang) {
                if (tuiGuang != null) {
                    viewHolder.setText(R.id.tv_item_mytuiguang_account, tuiGuang.getSubAccount());
                    viewHolder.setText(R.id.tv_item_mytuiguang_time, "注册时间:"+tuiGuang.getTime());
                    viewHolder.setText(R.id.tv_item_mytuiguang_status, "状态:" + tuiGuang.getStatus());
                    if (!TextUtils.isEmpty(tuiGuang.getRate())) {
                        viewHolder.setImageResource(R.id.iv_item_mytuiguang, RateData.getRate(tuiGuang.getRate()));
                    }

                }
            }
        });
        rvLayout.setLayoutManager(new LinearLayoutManager(getmActivity()));
        rvLayout.setAdapter(adapter);
        rvLayout.addOnScrollListener(new EndlessRecyclerOnScrollListener(getmActivity()){
            @Override
            public void onLoadNextPage(View view) {
                RecyclerViewStateUtils.next(getmActivity(), rvLayout, new RecyclerViewStateUtils.nextListener() {
                    @Override
                    public void load() {

                    }
                });

            }

            @Override
            public void show(boolean isShow) {
                RecyclerViewStateUtils.show(isShow,tuiGuangList,rvLayout,getmActivity());
            }
        });
    }

    @Override
    public void initListener() {
        topbarMytuiguang.setOnBackListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                back();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (RateData.rateMap != null) {
            RateData.rateMap.clear();
            RateData.rateMap=null;
        }
    }
}
