package com.betterda.betterdapay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.data.BankData;

import java.util.List;

/**
 * 银行卡item的适配器
 * Created by Administrator on 2017/4/13.
 */

public class MyYinHangKaItemAdapter<T> extends DelegateAdapter.Adapter<MyYinHangKaItemAdapter.MainViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private List<T> data;
    public MyYinHangKaItemAdapter(Context mContext, LayoutHelper mLayoutHelper, List<T> data ) {
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
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycleview_yinhangka, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
    /*    viewHolder.setText(R.id.tv_yinhangka_name, bankCard.getBank());
        viewHolder.setText(R.id.tv_yinhangka_number, bankCard.getCardNum());
        viewHolder.setText(R.id.tv_yinhangka_type, bankCard.getType());
        viewHolder.setImageResource(R.id.iv_yinhangka_icon, BankData.getBank(bankCard.getBank()));
        viewHolder.setOnClickListener(R.id.linear_yinhangka, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewHolder.getPosition();
                showDialog("温馨提示", "确定要删除该银行卡吗?");
            }
        });*/
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
        LinearLayout mLinearAdd;
        public MainViewHolder(View itemView) {
            super(itemView);

        }
    }
}
