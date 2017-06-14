package com.betterda.betterdapay.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.RateData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.FenRun;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.RecyclerViewStateUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.EndlessRecyclerOnScrollListener;
import com.betterda.betterdapay.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.mylibrary.LoadingPager;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 返还分润
 * Created by Administrator on 2016/8/15.
 */
public class FenRunFragment2 extends BaseFragment {

    @BindView(R.id.rv_fenrun2)
    RecyclerView rvFenrun2;
    @BindView(R.id.loadpager_fenrun2)
    LoadingPager loadpager_fenrun2;
    private List<FenRun> list;
    private List<FenRun> fenRunList;//存放服务器每次返回的数据
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private int item;//用来区分是返还分润还是推广分润
    private int page;


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_fenrun2, null);
    }

    @Override
    public void initData() {
        super.initData();
        getArgument();
        setRecycleView();
        getDataAndPage(1);
    }

    private void setRecycleView() {
        list = new ArrayList<>();
        fenRunList = new ArrayList<>();
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<FenRun>(getmActivity(), R.layout.item_recycleview_fenrun2, list) {

            @Override
            public void convert(ViewHolder viewHolder, FenRun fenRun) {
                if (fenRun != null) {
                    viewHolder.setText(R.id.tv_item_fenru2_account, fenRun.getAccount());
                    viewHolder.setText(R.id.tv_item_fenru2_rate, fenRun.getRate());
                    viewHolder.setText(R.id.tv_item_fenru2_name, fenRun.getName());

                    viewHolder.setText(R.id.tv_item_fenru2_money, fenRun.getMoney());
                    viewHolder.setText(R.id.tv_item_fenru2_time, fenRun.getTime());
                    viewHolder.setImageResource(R.id.iv_item_fenrun2, RateData.getRate(fenRun.getRate()));

                    switch (fenRun.getType()) {
                        case Constants.FEN_RUN_HUI:
                            viewHolder.setText(R.id.tv_item_fenru2_type, Constants.FEN_RUN_HUI2);
                            break;
                        case Constants.FEN_RUN_TUI:
                            viewHolder.setText(R.id.tv_item_fenru2_type, Constants.FEN_RUN_TUI2);
                            break;
                    }

                }
            }
        });
        rvFenrun2.setLayoutManager(new LinearLayoutManager(getmActivity()));
        rvFenrun2.setAdapter(adapter);
        rvFenrun2.addOnScrollListener(new EndlessRecyclerOnScrollListener(getmActivity()) {
            @Override
            public void onLoadNextPage(View view) {
                RecyclerViewStateUtils.next(getmActivity(), rvFenrun2, new RecyclerViewStateUtils.nextListener() {
                    @Override
                    public void load() {
                        getData();
                    }
                });
            }

            @Override
            public void show(boolean isShow) {

                RecyclerViewStateUtils.show(isShow, fenRunList, rvFenrun2, getmActivity());
            }
        });
    }

    private void getArgument() {
        Bundle arguments = getArguments();
        item = arguments.getInt("item");

    }

    public void getDataAndPage(int p) {
        page = p;
        loadpager_fenrun2.setLoadVisable();
        getData();
    }

    private void getData() {
        NetworkUtils.isNetWork(getmActivity(), loadpager_fenrun2, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                NetWork.getNetService()
                        .getOrderFenRun(UtilMethod.getAccout(getmActivity()),
                                UtilMethod.getToken(getmActivity()), item + "", page + "",
                                Constants.PAGE_SIZE + "")
                        .compose(NetWork.handleResult(new BaseCallModel<List<FenRun>>()))
                        .subscribe(new MyObserver<List<FenRun>>() {
                            @Override
                            protected void onSuccess(List<FenRun> data, String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    log(resultMsg + "success");
                                }
                                if (data != null) {
                                    parser(data);
                                }
                                UtilMethod.judgeData(list, loadpager_fenrun2);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    log(resultMsg);
                                }
                                loadpager_fenrun2.setErrorVisable();
                            }

                            @Override
                            public void onExit() {
                                loadpager_fenrun2.hide();
                                //解决预加载 显示2次对话框的问题
                                FenRunFragment parentFragment = (FenRunFragment) getParentFragment();
                                if (null != parentFragment && parentFragment.vpFenrun != null) {
                                    if (item == parentFragment.vpFenrun.getCurrentItem()) {
                                        ExitToLogin();
                                    }
                                }

                            }
                        });
            }
        });


    }

    private void parser(List<FenRun> data) {
        fenRunList = data;
        if (page == 1) {
            list.clear();
        }
        for (FenRun fenRun : fenRunList) {

            list.add(fenRun);
        }

        adapter.notifyDataSetChanged();

    }
}
