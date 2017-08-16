package com.betterda.betterdapay.javabean;

/**
 * 我的推广
 * Created by Administrator on 2016/8/17.
 */
public class TuiGuang {
    private String account;//帐号
    private String registerTime;//注册时间
    private String auth;//状态
    private String rankName;//等级
    private String rank;//等级id

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    @Override
    public String toString() {
        return "TuiGuang{" +
                "account='" + account + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", auth='" + auth + '\'' +
                ", rankName='" + rankName + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
