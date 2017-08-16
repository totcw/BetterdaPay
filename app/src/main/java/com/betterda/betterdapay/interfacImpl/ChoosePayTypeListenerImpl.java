package com.betterda.betterdapay.interfacImpl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.activity.MyYinHangKa;
import com.betterda.betterdapay.activity.QrCodeActicity;
import com.betterda.betterdapay.interfac.ChoosePayTypeListener;
import com.betterda.betterdapay.javabean.Rating;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.UtilMethod;
import com.zhy.base.adapter.ViewHolder;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/8/11
 * 功能说明： 支付通道处理逻辑的一个实现类
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class ChoosePayTypeListenerImpl implements ChoosePayTypeListener {
    @Override
    public void showRating(ViewHolder holder, Rating.RateDetail ratingCalculateEntity,  String money , Activity context) {
        if (holder != null && ratingCalculateEntity != null) {
            holder.setText(R.id.tv_item_choosepaytype, "单笔额度:" + UtilMethod.getMoney(ratingCalculateEntity.getTradeQuota()) + "元" +
                    ",当天额度:" + UtilMethod.getMoney(ratingCalculateEntity.getDayQuota()) + "元" + ",最低手续费:" + UtilMethod.getMoney(ratingCalculateEntity.getLeastTradeRate()) + "元");


            if (Constants.ZHIFUBAO.equals(ratingCalculateEntity.getTypeCode())) {
                holder.setImageResource(R.id.iv_my_information, R.mipmap.zhifubao);
            } else if (Constants.WEIXIN.equals(ratingCalculateEntity.getTypeCode())) {
                holder.setImageResource(R.id.iv_my_information, R.mipmap.weixin);
            } else {
                holder.setImageResource(R.id.iv_my_information, R.mipmap.yinlian);
            }

            holder.setText(R.id.tv_item_choosepaytype_type, ratingCalculateEntity.getTypeName());
            holder.setText(R.id.tv_item_choosepaytype_rating, "费率:"+ratingCalculateEntity.getTradeRate());

            holder.setOnClickListener(R.id.relative_choose_zhifubao, v -> chosePayType(ratingCalculateEntity.getTypeCode(), ratingCalculateEntity.getChannelId(),ratingCalculateEntity.getTradeQuota(),money,context,ratingCalculateEntity.getTypeName()));
        }
    }



    /**
     * 选择对应的通道
     * @param typeCode
     * @param tradeQuota
     * @param money
     * @param context
     */
    public void chosePayType(String typeCode, String channelId,String tradeQuota,String money,Activity context,String typeName) {
        try {
            Float payUp = Float.valueOf(money);
            Float trade = Float.valueOf(tradeQuota);
            int money2 = (int) (payUp * 100);
            if (payUp <= trade) {
                if (Constants.UNION_D0.equals(typeCode)||Constants.UNION_T1.equals(typeCode)
                        ||Constants.UNION_CONTROL_D0.equals(typeCode)||Constants.UNION_CONTROL_T1.equals(typeCode)) {
                    Intent intent = new Intent(context, MyYinHangKa.class);
                    intent.putExtra("isClick", true);
                    intent.putExtra("typeCode", typeCode);
                    intent.putExtra("money", money2);
                    intent.putExtra("channelId", channelId);
                    context.startActivity(intent);
                    //context.finish();
                } else if (Constants.ZHIFUBAO.equals(typeCode) || Constants.WEIXIN.equals(typeCode)) {
                    Intent intent = new Intent(context, QrCodeActicity.class);
                    intent.putExtra("typeCode", typeCode);
                    intent.putExtra("typeName", typeName);
                    intent.putExtra("money", money2);
                    intent.putExtra("channelId", channelId);
                    context.startActivity(intent);
                    //context.finish();
                } else {
                    UtilMethod.Toast(context,"交易通道出错");
                }

            } else {
                UtilMethod.Toast(context,"超过单笔额度");
            }

        } catch (Exception e) {

        }
    }
}
