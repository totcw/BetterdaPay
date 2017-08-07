package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
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

    private String rate = "员工";
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
        // topbarMyrating.setOnActionListener(this);
    }

    @Override
    public void init() {
        setTopBar();
        getRate();
        setRecycleview();
        getData();
        loadingPager.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        loadingPager.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadingPager, new NetworkUtils.SetDataInterface() {
            @Override
            public void getDataApi() {
                mRxManager.add(
                        NetWork.getNetService()
                                .getRatingForMe(UtilMethod.getAccout(getmActivity()))
                                .compose(NetWork.handleResult(new BaseCallModel<Rating>()))
                                .subscribe(new MyObserver<Rating>() {
                                    @Override
                                    protected void onSuccess(Rating data, String resultMsg) {

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
                );
            }
        });
    }

    private void parser(Rating data) {

        if (list != null) {
            list.clear();
            for (Rating.RateDetail rateDetail : data.getRates()) {
                list.add(rateDetail);
            }
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
                log(rating.getIntroduce());
                if (rating != null) {
                    viewHolder.setText(R.id.tv_item_up_name, rating.getType());
                    viewHolder.setText(R.id.tv_item_up_rating, rating.getT1TradeRate());
                    viewHolder.setText(R.id.tv_item_up_rating2, rating.getT0TradeRate());
                    viewHolder.setText(R.id.tv_item_up_jiesuan, rating.getT1DrawFee());
                    viewHolder.setText(R.id.tv_item_up_jiesuan2, rating.getT0DrawFee());
                    viewHolder.setText(R.id.tv_item_up_edu, rating.getT1TradeQuota()+","+rating.getT1DayQuota());
                    viewHolder.setText(R.id.tv_item_up_edu2, rating.getT0TradeQuota()+","+rating.getT0DayQuota());
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
        //  topbarMyrating.setActionText("升级");
        topbarMyrating.setTitle("我的扣率");
        //topbarMyrating.setActionTextVisibility(true);
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
