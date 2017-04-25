package com.betterda.betterdapay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.betterda.betterdapay.R;

/**
 * 办理信用卡轮播图的适配器
 * Created by Administrator on 2017/4/13.
 */

public class BanliCreditBannerAdapter extends DelegateAdapter.Adapter<BanliCreditBannerAdapter.MainViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private int mCount = 0;

    public BanliCreditBannerAdapter(Context mContext,LayoutHelper mLayoutHelper, int mCount) {
        this.mLayoutHelper = mLayoutHelper;
        this.mCount = mCount;
        this.mContext = mContext;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_banlicredit_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mCount;
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }
}
