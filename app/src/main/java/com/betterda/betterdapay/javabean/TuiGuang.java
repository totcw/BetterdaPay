package com.betterda.betterdapay.javabean;

/**
 * 我的推广
 * Created by Administrator on 2016/8/17.
 */
public class TuiGuang {
    private String account;//帐号
    private String time;//注册时间
    private String auth;//状态
    private String rank;//等级

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

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "TuiGuang{" +
                "account='" + account + '\'' +
                ", time='" + time + '\'' +
                ", auth='" + auth + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
