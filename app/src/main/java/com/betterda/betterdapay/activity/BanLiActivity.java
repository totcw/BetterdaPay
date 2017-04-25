package com.betterda.betterdapay.activity;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.adapter.BanliCreditBannerAdapter;
import com.betterda.betterdapay.adapter.BanliCreditItemAdapter;
import com.betterda.betterdapay.adapter.BanliCreditThemeAdapter;
import com.betterda.betterdapay.javabean.CreditType;
import com.betterda.betterdapay.view.NormalTopBar;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 办理信用卡
 * Created by Administrator on 2017/3/29.
 */

public class BanLiActivity extends BaseActivity {
    @BindView(R.id.topbar_banli)
    NormalTopBar mTopbarBanli;
    @BindView(R.id.rv_banli)
    RecyclerView mRvBanli;

    private List<CreditType> mBanliList;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_banli);

    }

    @Override
    public void init() {
        super.init();
        mTopbarBanli.setTitle("办理信用卡");
        initRecycleview();
    }

    private void initRecycleview() {
        mBanliList = new ArrayList<>();
        mBanliList.add(null);
        mBanliList.add(null);
        mBanliList.add(null);
        mBanliList.add(null);
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getmActivity());
        mRvBanli.setLayoutManager(virtualLayoutManager);
        mRvBanli.addItemDecoration(new DividerItemDecoration(getmActivity(), DividerItemDecoration.VERTICAL_LIST));
        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）：
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRvBanli.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        //加载数据时有两种方式:一种是使用 DelegateAdapter, 可以像平常一样写继承自DelegateAdapter.Adapter的Adapter, 只比之前的Adapter需要多重载onCreateLayoutHelper方法。 其他的和默认Adapter一样。
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //给rv 设置 一个adapter
        mRvBanli.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(new BanliCreditBannerAdapter(getmActivity(), new LinearLayoutHelper(), 1));
        delegateAdapter.addAdapter(new BanliCreditThemeAdapter(getmActivity(), new LinearLayoutHelper(), 1));
        delegateAdapter.addAdapter(new BanliCreditItemAdapter(getmActivity(), new LinearLayoutHelper(), mBanliList));



    }



    @OnClick(R.id.bar_back)
    public void onClick() {
        back();
    }
}
