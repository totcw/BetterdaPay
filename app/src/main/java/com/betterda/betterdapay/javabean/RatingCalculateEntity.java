package com.betterda.betterdapay.javabean;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/21
 * 功能说明：用于计算的费率的实体
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class RatingCalculateEntity {
    private String t1TradeRate;//隔天费率
    private String t0TradeRate;//实时费率
    private String t1DrawFee;//隔天结算笔数
    private String t0DrawFee;//实时结算笔数
    private String t0LeastTradeRate;//t0最小手续费
    private String t1LeastTradeRate;//t1最小手续费
    private String t0TradeQuota;//t0单笔额度
    private String t0DayQuota;//t0当天额度
    private String t1TradeQuota;//t1当天额度
    private String t1DayQuota;//t1当天额度
    private String payWay;//通道类型

    public RatingCalculateEntity() {
    }

    public RatingCalculateEntity(String payWay) {
        this.payWay = payWay;
    }

    public String getT1TradeRate() {
        return t1TradeRate;
    }

    public void setT1TradeRate(String t1TradeRate) {
        this.t1TradeRate = t1TradeRate;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getT1DayQuota() {
        return t1DayQuota;
    }

    public void setT1DayQuota(String t1DayQuota) {
        this.t1DayQuota = t1DayQuota;
    }

    public String getT1TradeQuota() {
        return t1TradeQuota;
    }

    public void setT1TradeQuota(String t1TradeQuota) {
        this.t1TradeQuota = t1TradeQuota;
    }

    public String getT0DayQuota() {
        return t0DayQuota;
    }

    public void setT0DayQuota(String t0DayQuota) {
        this.t0DayQuota = t0DayQuota;
    }

    public String getT0TradeQuota() {
        return t0TradeQuota;
    }

    public void setT0TradeQuota(String t0TradeQuota) {
        this.t0TradeQuota = t0TradeQuota;
    }

    public String getT1LeastTradeRate() {
        return t1LeastTradeRate;
    }

    public void setT1LeastTradeRate(String t1LeastTradeRate) {
        this.t1LeastTradeRate = t1LeastTradeRate;
    }

    public String getT0LeastTradeRate() {
        return t0LeastTradeRate;
    }

    public void setT0LeastTradeRate(String t0LeastTradeRate) {
        this.t0LeastTradeRate = t0LeastTradeRate;
    }

    public String getT0DrawFee() {
        return t0DrawFee;
    }

    public void setT0DrawFee(String t0DrawFee) {
        this.t0DrawFee = t0DrawFee;
    }

    public String getT1DrawFee() {
        return t1DrawFee;
    }

    public void setT1DrawFee(String t1DrawFee) {
        this.t1DrawFee = t1DrawFee;
    }

    public String getT0TradeRate() {
        return t0TradeRate;
    }

    public void setT0TradeRate(String t0TradeRate) {
        this.t0TradeRate = t0TradeRate;
    }

    @Override
    public String toString() {
        return "RatingCalculateEntity{" +
                "t1TradeRate='" + t1TradeRate + '\'' +
                ", t0TradeRate='" + t0TradeRate + '\'' +
                ", t1DrawFee='" + t1DrawFee + '\'' +
                ", t0DrawFee='" + t0DrawFee + '\'' +
                ", t0LeastTradeRate='" + t0LeastTradeRate + '\'' +
                ", t1LeastTradeRate='" + t1LeastTradeRate + '\'' +
                ", t0TradeQuota='" + t0TradeQuota + '\'' +
                ", t0DayQuota='" + t0DayQuota + '\'' +
                ", t1TradeQuota='" + t1TradeQuota + '\'' +
                ", t1DayQuota='" + t1DayQuota + '\'' +
                ", payWay='" + payWay + '\'' +
                '}';
    }
}
