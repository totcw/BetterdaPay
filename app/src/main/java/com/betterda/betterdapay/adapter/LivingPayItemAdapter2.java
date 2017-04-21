package com.betterda.betterdapay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.livingpay.LivingPay;

import java.util.List;

/**
 * 生活缴费item
 * Created by Administrator on 2017/4/13.
 */

public class LivingPayItemAdapter2 extends DelegateAdapter.Adapter<LivingPayItemAdapter2.MainViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private List<LivingPay> data;
    public LivingPayItemAdapter2(Context mContext, LayoutHelper mLayoutHelper, List<LivingPay> data ) {
        this.mLayoutHelper = mLayoutHelper;
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_livingpay, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (null != data) {
            return data.size();
        } else {
            return 0;
        }
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }
}
