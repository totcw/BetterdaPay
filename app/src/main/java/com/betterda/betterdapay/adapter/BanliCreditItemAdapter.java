package com.betterda.betterdapay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.betterda.betterdapay.R;
import com.betterda.betterdapay.javabean.CreditType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 办理信用卡item适配器
 * Created by Administrator on 2017/4/13.
 */

public class BanliCreditItemAdapter<T> extends DelegateAdapter.Adapter<BanliCreditItemAdapter.MainViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private List<CreditType> data;

    public BanliCreditItemAdapter(Context mContext, LayoutHelper mLayoutHelper, List<CreditType> data) {
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
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_tyepcard, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        if (holder != null && data != null && data.get(position) != null) {

            holder.mTvItemTypecardBankname.setText(data.get(position).getBankName());
            holder.mTvItemTypecardBankrate.setText(data.get(position).getBankRate());
            holder.mTvItemTypecardNumber.setText(data.get(position).getNumber());
            holder.mTvItemTypecardIntroduce.setText(data.get(position).getIntroduce());
            holder.mTvItemTypecardJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
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
        @BindView(R.id.iv_item_typecard_url)
        ImageView mIvItemTypecardUrl;
        @BindView(R.id.tv_item_typecard_bankname)
        TextView mTvItemTypecardBankname;
        @BindView(R.id.tv_item_typecard_bankrate)
        TextView mTvItemTypecardBankrate;
        @BindView(R.id.tv_item_typecard_introduce)
        TextView mTvItemTypecardIntroduce;
        @BindView(R.id.tv_item_typecard_number)
        TextView mTvItemTypecardNumber;
        @BindView(R.id.tv_item_typecard_join)
        TextView mTvItemTypecardJoin;

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }


}
