package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.interfac.ChoosePayTypeListener;
import com.betterda.betterdapay.interfacImpl.ChoosePayTypeListenerImpl;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.javabean.Rating;
import com.betterda.betterdapay.javabean.RatingCalculateEntity;
import com.betterda.betterdapay.util.CacheUtils;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.LocationUtil;
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
import butterknife.OnClick;

/**
 * 选择支付通道(收款)
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
    private CommonAdapter<Rating.RateDetail> mAdapter;

    private List<Rating.RateDetail> mList;
    private String money;//支付金额
    private String longitude;//经度
    private String latitude ;//
    private String province ;//
    private String city ;//
    private String area ;//
    private String street ;//

    private ChoosePayTypeListener mChoosePayTypeListener;

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
        mChoosePayTypeListener = new ChoosePayTypeListenerImpl();
        initRecycleView();

        mLoadingPager.setonErrorClickListener(v -> {
            mLoadingPager.setLoadVisable();
            getDataForRate();
        });
        mLoadingPager.setLoadVisable();
        LocationUtil locationUtil = new LocationUtil();
        locationUtil.start(getmActivity(), location -> {

            if (location != null) {
                longitude = location.getLongitude()+"";
                latitude = location.getLatitude()+"";
                province = location.getProvince();
                city = location.getCity();
                area = location.getDistrict();
                street = location.getStreet();
            }

            CacheUtils.putString(getmActivity(),"longitude",longitude);
            CacheUtils.putString(getmActivity(),"latitude",latitude);
            CacheUtils.putString(getmActivity(),"province",province);
            CacheUtils.putString(getmActivity(),"city",city);
            CacheUtils.putString(getmActivity(),"area",area);
            CacheUtils.putString(getmActivity(),"street",street);

            runOnUiThread(() -> getDataForRate());
        });

    }

    private void initRecycleView() {
        mList = new ArrayList<>();
        mAdapter = new CommonAdapter<Rating.RateDetail>(this, R.layout.rv_item_choosepaytype, mList) {
            @Override
            public void convert(ViewHolder holder, final Rating.RateDetail ratingCalculateEntity) {
                if (mChoosePayTypeListener != null) {
                    mChoosePayTypeListener.showRating(holder,ratingCalculateEntity,money,getmActivity());
                }
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
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
            case R.id.bar_back:
                back();
                break;
        }
    }

    /**
     * 获取各个通道的费率
     */
    private void getDataForRate() {

        NetworkUtils.isNetWork(getmActivity(), mLoadingPager, () -> mRxManager.add(
                NetWork.getNetService()
                        .getRatingForMe(UtilMethod.getAccout(getmActivity()),getString(R.string.appCode))
                        .compose(NetWork.handleResult(new BaseCallModel<List<Rating.RateDetail>>()))
                        .subscribe(new MyObserver<List<Rating.RateDetail>>() {
                            @Override
                            protected void onSuccess(List<Rating.RateDetail> data, String resultMsg) {
                                if (BuildConfig.LOG_DEBUG) {
                                    System.out.println("收款:" + data);
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
                                    System.out.println("收款fail:" + resultMsg);
                                }
                                if (mLoadingPager != null) {
                                    mLoadingPager.setErrorVisable();
                                }
                            }

                            @Override
                            public void onExit(String resultMsg) {
                                ExitToLogin(resultMsg);
                            }
                        })
        ));
    }

    private void parserData(List<Rating.RateDetail> data) {
        if (mList != null) {
            mList.addAll(data);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }




}
