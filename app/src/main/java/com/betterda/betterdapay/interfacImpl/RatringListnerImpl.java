package com.betterda.betterdapay.interfacImpl;

import com.betterda.betterdapay.R;
import com.betterda.betterdapay.interfac.RatingListener;
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
 * 功能说明：费率接口的实现类
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class RatringListnerImpl implements RatingListener{
    @Override
    public void showRating(ViewHolder viewHolder, Rating.RateDetail rating) {
        if (rating != null) {
            viewHolder.setText(R.id.tv_item_up_typename, rating.getTypeName());
            viewHolder.setText(R.id.tv_item_up_name, rating.getType());
            viewHolder.setText(R.id.tv_item_up_rating, rating.getTradeRate());
            viewHolder.setText(R.id.tv_item_up_edu, "单笔额度:" + UtilMethod.getMoney(rating.getTradeQuota()) + "元" +
                    ",当天额度:" + UtilMethod.getMoney(rating.getDayQuota()) + "元");
            if (Constants.ZHIFUBAO.equals(rating.getTypeCode())) {
                viewHolder.setImageResource(R.id.iv_item_up, R.mipmap.zhifubao);
            } else if (Constants.WEIXIN.equals(rating.getTypeCode())) {
                viewHolder.setImageResource(R.id.iv_item_up, R.mipmap.weixin);
            } else {
                viewHolder.setImageResource(R.id.iv_item_up, R.mipmap.yinlian);
            }
        }
    }
}
