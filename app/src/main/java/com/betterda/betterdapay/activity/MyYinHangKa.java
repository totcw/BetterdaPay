package com.betterda.betterdapay.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.adapter.MyYinHangKaAddAdapter;
import com.betterda.betterdapay.adapter.MyYinHangKaItemAdapter;
import com.betterda.betterdapay.callback.MyObserver;
import com.betterda.betterdapay.data.BankData;
import com.betterda.betterdapay.http.NetWork;
import com.betterda.betterdapay.javabean.BankCard;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;
import com.betterda.betterdapay.util.UtilMethod;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的信用卡
 * Created by Administrator on 2016/8/16.
 */
public class MyYinHangKa extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.topbar_myyinhangka)
    NormalTopBar topbarMyyinhangka;
    @BindView(R.id.rv_layout)
    RecyclerView rvLayout;
    @BindView(R.id.loadpager_layout)
    LoadingPager loadpagerLayout;

    private MyYinHangKaItemAdapter<BankCard> mItemAdapter;
    private List<BankCard> list;
    private int money;
    private boolean isClick;
    private boolean isPay;//是否是付款
    private String rankId,rank;//升级的id

    @Override
    public void initView() {
        setContentView(R.layout.activity_myyinhangka);
    }

    @Override
    public void init() {
        setTopBar();
        getIntentData();
        setRecycleview();
        loadpagerLayout.setonErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }
    private void getIntentData() {
        Intent intent = getIntent();
        money= intent.getIntExtra("money", 0);
        rankId = intent.getStringExtra("rankId");
        rank = intent.getStringExtra("rank");
        isClick = intent.getBooleanExtra("isClick", false);
        isPay = intent.getBooleanExtra("isPay", false);
    }
    /**
     * 获取银行卡信息
     */
    private void getData() {
        loadpagerLayout.setLoadVisable();
        NetworkUtils.isNetWork(getmActivity(), loadpagerLayout, () -> NetWork.getNetService()
                .getBandGet(UtilMethod.getAccout(getmActivity()), Constants.APPCODE)
                .compose(NetWork.handleResult(new BaseCallModel<>()))
                .subscribe(new MyObserver<List<BankCard>>() {
                    @Override
                    protected void onSuccess(List<BankCard> data, String resultMsg) {
                        if (BuildConfig.LOG_DEBUG) {
                            System.out.println("获取银行卡:"+data);
                        }
                        if (data != null) {
                            parser(data);
                        }
                        loadpagerLayout.hide();
                    }

                    @Override
                    public void onFail(String resultMsg) {
                        if (BuildConfig.LOG_DEBUG) {
                            System.out.println("获取银行卡:"+resultMsg);
                        }
                        loadpagerLayout.setErrorVisable();
                    }

                    @Override
                    public void onExit(String resultMsg) {
                        ExitToLogin(resultMsg);
                    }
                }));
    }

    /**
     * 解析服务器返回的数据
     * @param data
     */
    private void parser(List<BankCard> data) {
        list.clear();
        list.addAll(data);
        if (mItemAdapter != null) {
            mItemAdapter.notifyDataSetChanged();
        }
    }

    private void setRecycleview() {
        list = new ArrayList<>();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getmActivity());

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）：
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        rvLayout.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        rvLayout.setLayoutManager(virtualLayoutManager);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        rvLayout.setAdapter(delegateAdapter);
        rvLayout.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int padding = UtilMethod.dip2px(getmActivity(), 10);
                int padding2 = UtilMethod.dip2px(getmActivity(), 5);
                outRect.set(padding,padding2,padding,padding2);
            }
        });
        mItemAdapter = new MyYinHangKaItemAdapter<BankCard>(getmActivity(),new LinearLayoutHelper(),list,isClick,isPay,money,rankId,rank);
        delegateAdapter.addAdapter(mItemAdapter);

        delegateAdapter.addAdapter(new MyYinHangKaAddAdapter(getmActivity(),new LinearLayoutHelper(),1));

    }

    private void setTopBar() {
        topbarMyyinhangka.setTitle("我的信用卡");

    }

    @Override
    public void initListener() {
        super.initListener();

        topbarMyyinhangka.setOnBackListener(this);
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
        if (BankData.bankMap != null) {
            BankData.bankMap.clear();
            BankData.bankMap = null;
        }
    }
}
