package com.betterda.betterdapay.interfac;

import android.app.Activity;
import android.content.Context;

import com.betterda.betterdapay.javabean.Rating;
import com.zhy.base.adapter.ViewHolder;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/8/11
 * 功能说明： 选择支付通道的接口
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public interface ChoosePayTypeListener {
    /**
     * 显示通道
     * @param viewHolder
     * @param rating
     */
    void showRating(ViewHolder viewHolder, Rating.RateDetail rating, String money , Activity context);



}
