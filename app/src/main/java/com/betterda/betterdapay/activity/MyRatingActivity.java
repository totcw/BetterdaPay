package com.betterda.betterdapay.activity;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.interfac.RatingListener;
import com.betterda.betterdapay.interfacImpl.RatringListnerImpl;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Rating;
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

    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<Rating.RateDetail> list;

    private RatingListener mRatingListener;//显示费率逻辑的接口

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_myrating);
    }

    @Override
    public void initListener() {
        super.initListener();
        topbarMyrating.setOnBackListener(this);
        // topbarMyrating.setOnActionListener(this);
    }

    @Override
    public void init() {
        setTopBar();
        mRatingListener = new RatringListnerImpl();
        setRecycleview();
        getData();
        loadingPager.setonErrorClickListener(v -> getData());

    }

    private void getData() {
        loadingPager.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadingPager, () -> mRxManager.add(
                NetWork.getNetService()
                        .getRatingForMe(UtilMethod.getAccout(getmActivity()),getString(R.string.appCode))
                        .compose(NetWork.handleResult(new BaseCallModel<>()))
                        .subscribe(new MyObserver<List<Rating.RateDetail>>() {
                            @Override
                            protected void onSuccess(List<Rating.RateDetail> data, String resultMsg) {

                                if (data != null) {
                                    parser(data);
                                }
                                UtilMethod.judgeData(list, loadingPager);
                            }

                            @Override
                            public void onFail(String resultMsg) {
                                if (loadingPager != null) {
                                    loadingPager.setErrorVisable();
                                }
                            }

                            @Override
                            public void onExit(String resultMsg) {
                                ExitToLogin(resultMsg);
                            }
                        })
        ));
    }

    private void parser(List<Rating.RateDetail> data) {

        if (list != null) {
            list.clear();
            list.addAll(data);
        }


        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    private void setRecycleview() {
        list = new ArrayList<>();
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Rating.RateDetail>(getmActivity(), R.layout.item_recycleview_up, list) {

            @Override
            public void convert(ViewHolder viewHolder, Rating.RateDetail rating) {
                if (mRatingListener != null) {
                    mRatingListener.showRating(viewHolder,rating);
                }

            }
        });
        rvMyrating.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int padding = UtilMethod.dip2px(getmActivity(), 8);
                int padding2 = UtilMethod.dip2px(getmActivity(), 4);
                outRect.set(padding,padding2,padding,padding2);
            }
        });
        rvMyrating.setAdapter(adapter);
        rvMyrating.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setTopBar() {
        topbarMyrating.setTitle("我的扣率");
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
