package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.http.NetWork;
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
 * 选择支付通道(付款)
 * Created by Administrator on 2016/8/4.
 */
public class ChoosePayTypePayActivity extends BaseActivity {


    @BindView(R.id.topbar_chose)
    NormalTopBar topbarChose;
    @BindView(R.id.rv_choosepaytype)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadpager_choosepaytype)
    LoadingPager mLoadingPager;

    private CommonAdapter<Rating.RateDetail> mAdapter;
    private List<Rating.RateDetail> mList;
    private String payUp;//支付金额
    private String rankName;//升级到指定等级
    private String rank;//升级到的等级id
    private int mMoney;//单位为分

    private String longitude;//经度
    private String latitude ;//
    private String province ;//
    private String city ;//
    private String area ;//
    private String street ;//

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_choosepaytypepay);

    }

    @Override
    public void init() {
        super.init();
        getInitData();
        topbarChose.setTitle("选择支付通道");
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
            public void convert(final ViewHolder holder, Rating.RateDetail ratingCalculateEntity) {
                if (holder != null && ratingCalculateEntity != null) {
                    holder.setText(R.id.tv_item_choosepaytype, "单笔额度:" + ratingCalculateEntity.getTradeQuota() + "元" +
                            ",当天额度:" + ratingCalculateEntity.getDayQuota() + "元" + "最低手续费:" + ratingCalculateEntity.getLeastTradeRate() + "元");


                    if (Constants.ZHIFUBAO.equals(ratingCalculateEntity.getTypeCode())) {
                        holder.setImageResource(R.id.iv_my_information, R.mipmap.zhifubao);
                    } else if (Constants.WEIXIN.equals(ratingCalculateEntity.getTypeCode())) {
                        holder.setImageResource(R.id.iv_my_information, R.mipmap.weixin);
                    } else {
                        holder.setImageResource(R.id.iv_my_information, R.mipmap.yinlian);
                    }

                    holder.setText(R.id.tv_item_choosepaytype_type, ratingCalculateEntity.getTypeName());
                    holder.setText(R.id.tv_item_choosepaytype_rating, "费率:"+ratingCalculateEntity.getTradeRate());

                    if (Constants.UNION_CONTROL_T1.equals(ratingCalculateEntity.getTypeCode())) {
                        holder.setVisible(R.id.relative_choose_zhifubao, true);
                    } else {
                        holder.setVisible(R.id.relative_choose_zhifubao, false);
                    }

                    holder.setOnClickListener(R.id.relative_choose_zhifubao, v -> {
                        if (holder.getAdapterPosition() == 0) {
                            Intent intent = new Intent(getmActivity(), MyYinHangKa.class);
                            intent.putExtra("rank", rank);
                            intent.putExtra("rankName", rankName);
                            intent.putExtra("channelId", ratingCalculateEntity.getChannelId());
                            intent.putExtra("isPay", true);
                            intent.putExtra("money", mMoney);
                            intent.putExtra("isClick", true);
                            startActivity(intent);
                            finish();
                        } else {
                            //TODO 无跳转
                        }
                    });
                }
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 获取数据
     */
    private void getInitData() {
        Intent intent = getIntent();
        if (intent != null) {
            payUp = intent.getStringExtra("money");
            rankName = intent.getStringExtra("rankName");
            rank = intent.getStringExtra("rank");
        }
        if (TextUtils.isEmpty(payUp)) {
            payUp = "0";
        }
        try {
            mMoney = (int) (Float.valueOf(payUp) * 100);
        } catch (Exception e) {

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




    @OnClick({R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                back();
                break;
        }
    }




}
