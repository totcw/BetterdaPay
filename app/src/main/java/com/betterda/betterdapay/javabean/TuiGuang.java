package com.betterda.betterdapay.javabean;

/**
 * 我的推广
 * Created by Administrator on 2016/8/17.
 */
public class TuiGuang {
    private String subAccount;//帐号
    private String time;//注册时间
    private String status;//状态
    private String rate;//等级

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
