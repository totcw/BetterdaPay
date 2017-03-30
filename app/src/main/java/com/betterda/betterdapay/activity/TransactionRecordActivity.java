package com.betterda.betterdapay.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.view.NormalTopBar;
import com.betterda.mylibrary.LoadingPager;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 交易记录
 * Created by Administrator on 2017/3/30.
 */

public class TransactionRecordActivity extends BaseActivity {

    @BindView(R.id.topbar_tranastionrecord)
    NormalTopBar mTopbarTranastionrecord;
    @BindView(R.id.rv_layout)
    RecyclerView mRvLayout;
    @BindView(R.id.loadpager_layout)
    LoadingPager mLoadpagerLayout;

    private List<String> list;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_transactionrecord);
    }

    @Override
    public void init() {
        super.init();
        initRv();
    }

    private void initRv() {
        list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        mRvLayout.setLayoutManager(new LinearLayoutManager(getmActivity()));
        mRvLayout.setAdapter(new CommonAdapter<String>(getmActivity(),R.layout.item_recycleview_tranastionreord,list) {
            @Override
            public void convert(ViewHolder holder, String o) {

            }


        });
    }

    @OnClick({R.id.bar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_back:

                break;

        }
    }
}
