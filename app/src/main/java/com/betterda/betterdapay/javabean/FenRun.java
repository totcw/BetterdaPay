package com.betterda.betterdapay.javabean;

import javax.inject.Inject;

/**
 * 分润
 * Created by Administrator on 2016/8/15.
 */


public class FenRun {
    private String rate;//分润等级
    private String name;//分润姓名
    private String profitType;//分润类型
    private String money;//分润金额
    private String account;//用户
    private String time;//时间

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return profitType;
    }

    public void setType(String type) {
        this.profitType = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "FenRun{" +
                "rate='" + rate + '\'' +
                ", name='" + name + '\'' +
                ", type='" + profitType + '\'' +
                ", money='" + money + '\'' +
                ", account='" + account + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
