package com.betterda.betterdapay.interfac;

import com.betterda.betterdapay.javabean.Rating;
import com.zhy.base.adapter.ViewHolder;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/8/11
 * 功能说明： 处理费率相关的接口
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public interface RatingListener {
    /**
     * 显示费率
     * @param viewHolder
     * @param rating
     */
    void showRating(ViewHolder viewHolder, Rating.RateDetail rating);
}
