package com.betterda.betterdapay.livingpay;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.BaseActivity;
import com.betterda.betterdapay.adapter.LivingPayItemAdapter;
import com.betterda.betterdapay.adapter.LivingPayItemAdapter2;
import com.betterda.betterdapay.view.NormalTopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 生活缴费
 * Created by Administrator on 2016/9/8.
 */
public class BaseLivingActiivty extends BaseActivity {


    @BindView(R.id.topbar_baseliving)
    NormalTopBar mTopbarBaseliving;
    @BindView(R.id.iv_baseliving_icon)
    ImageView mIvBaselivingIcon;
    @BindView(R.id.tv_baseliving_city)
    TextView mTvBaselivingCity;
    @BindView(R.id.relative_baseliving)
    RelativeLayout mRelativeBaseliving;
    @BindView(R.id.rv_baseliving)
    RecyclerView mRvBaseliving;
    private LivingPayItemAdapter2 mWalletAdapter,mEleAdapter,mGasAdapter;
    private List<LivingPay> mWalletList,mEleList,mGasList;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_baseliving);

    }


    @Override
    public void init() {
        super.init();
        setTopBar();
        initRv();
    }

    private void initRv() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getmActivity());
        mRvBaseliving.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRvBaseliving.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        mRvBaseliving.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(new LivingPayItemAdapter(getmActivity(),new LinearLayoutHelper(),1,"水费"));
        mWalletList = new ArrayList<>();

        mWalletAdapter = new LivingPayItemAdapter2(getmActivity(), new LinearLayoutHelper(), mWalletList);
        delegateAdapter.addAdapter(mWalletAdapter);

        delegateAdapter.addAdapter(new LivingPayItemAdapter(getmActivity(),new LinearLayoutHelper(),1,"电费"));
        mEleList = new ArrayList<>();
        mEleList.add(null);
        mEleList.add(null);
        mEleList.add(null);
        mEleAdapter = new LivingPayItemAdapter2(getmActivity(), new LinearLayoutHelper(), mEleList);
        delegateAdapter.addAdapter(mEleAdapter);

        delegateAdapter.addAdapter(new LivingPayItemAdapter(getmActivity(),new LinearLayoutHelper(),1,"燃气费"));
        mGasList = new ArrayList<>();
        mGasList.add(null);
        mGasList.add(null);
        mGasList.add(null);
        mGasAdapter = new LivingPayItemAdapter2(getmActivity(), new LinearLayoutHelper(), mGasList);
        delegateAdapter.addAdapter(mGasAdapter);
    }

    private void setTopBar() {
        mTopbarBaseliving.setTitle("生活缴费");
        mTopbarBaseliving.setActionText("缴费记录");
        mTopbarBaseliving.setActionTextVisibility(true);
    }




    @OnClick({R.id.bar_back,R.id.bar_action, R.id.relative_baseliving})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:
                break;
            case R.id.bar_action:
                break;
            case R.id.relative_baseliving:
                break;
        }
    }
}
