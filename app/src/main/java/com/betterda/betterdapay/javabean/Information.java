package com.betterda.betterdapay.javabean;

/**
 * 我的资料获取
 * Created by Administrator on 2016/9/2.
 */
public class Information {
    private String account;
    private String rate;//等级
    private String auth; //是否认证 true false
    private String realname; //真是姓名
    private String identityCard;//身份证号
    private String bankcard;//银行卡号
    private String bankname;//银行名

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdentityCar() {
        return identityCard;
    }

    public void setIdentityCar(String identityCar) {
        this.identityCard = identityCar;
    }

    @Override
    public String toString() {
        return "Information{" +
                "account='" + account + '\'' +
                ", rate='" + rate + '\'' +
                ", auth='" + auth + '\'' +
                ", realname='" + realname + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", bankcard='" + bankcard + '\'' +
                ", bankname='" + bankname + '\'' +
                '}';
    }
}
