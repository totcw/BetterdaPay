package com.betterda.betterdapay.activity;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.BuildConfig;
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

    public final static String AUTH_ALREAD_CODE = "1";/*已经认证的状态码*/
    public final static String AUTH_ALREAD = "已认证";/*已经认证*/
    public final static String AUTH_NO = "未认证";/*未认证*/
    private List<TuiGuang> list, tuiGuangList;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private int page = 1;
    private String start=page+"";//分页的开始下标
    @Override
    public void initView() {
        setContentView(R.layout.activity_mytuiguang);
    }

    @Override
    public void init() {
        topbarMytuiguang.setTitle("我的推广");
        setRecycleview();
        loadpagerLayout.setLoadVisable();
        getData();
        loadpagerLayout.setonErrorClickListener(v -> {
            loadpagerLayout.setLoadVisable();
            page = 1;
            start = "1";
            getData();
        });
    }

    private void getData() {

        NetworkUtils.isNetWork(getmActivity(), loadpagerLayout, () -> mRxManager.add(
                NetWork.getNetService()
                        .getSub(UtilMethod.getAccout(getmActivity()),start, Constants.PAGE_SIZE+"",getString(R.string.appCode))
                        .compose(NetWork.handleResult(new BaseCallModel<>()))
                        .subscribe(new MyObserver<List<TuiGuang>>() {
                            @Override
                            protected void onSuccess(List<TuiGuang> data, String resultMsg) {

                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("我的推广:"+data);
                                }
                                if (data != null) {
                                    parser(data);
                                }
                                UtilMethod.judgeData(list, loadpagerLayout);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("我的推广fails:"+resultMsg);
                                }
                                if (loadpagerLayout != null) {
                                    loadpagerLayout.setErrorVisable();
                                }
                            }

                            @Override
                            public void onExit(String resultMsg) {
                                ExitToLogin(resultMsg);
                            }
                        })
        ));
    }

    private void parser(List<TuiGuang> data) {
        tuiGuangList = data;
        if (list != null) {
            if (page == 1) {
                list.clear();
            }
            list.addAll(data);
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
                    viewHolder.setText(R.id.tv_item_mytuiguang_account, tuiGuang.getRankName());
                    viewHolder.setText(R.id.tv_item_mytuiguang_number, UtilMethod.transforPhoneNumber(tuiGuang.getAccount()));
                    viewHolder.setText(R.id.tv_item_mytuiguang_time, "注册时间:" + tuiGuang.getRegisterTime());

                    viewHolder.setImageResource(R.id.iv_item_mytuiguang, RateData.getRate(tuiGuang.getRank()));

                    if (AUTH_ALREAD_CODE.equals(tuiGuang.getAuth())) {
                        viewHolder.setText(R.id.tv_item_mytuiguang_renzheng, AUTH_ALREAD);
                    } else {
                        viewHolder.setText(R.id.tv_item_mytuiguang_renzheng, AUTH_NO);
                    }

                }
            }
        });
        rvLayout.setLayoutManager(new LinearLayoutManager(getmActivity()));
        rvLayout.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                // super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10, 10, 10, 10);
            }
        });
        rvLayout.setAdapter(adapter);
        rvLayout.addOnScrollListener(new EndlessRecyclerOnScrollListener(getmActivity()) {
            @Override
            public void onLoadNextPage(View view) {
                RecyclerViewStateUtils.next(getmActivity(), rvLayout, () -> {
                    page++;
                    start = UtilMethod.getStart(page);
                    getData();
                });

            }

            @Override
            public void show(boolean isShow) {
                //这里是要传当前服务器返回的list
                RecyclerViewStateUtils.show(isShow, tuiGuangList, rvLayout, getmActivity());
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
            RateData.rateMap = null;
        }
    }
}
