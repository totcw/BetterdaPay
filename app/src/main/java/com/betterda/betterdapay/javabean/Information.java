package com.betterda.betterdapay.javabean;

/**
 * 我的资料获取
 * Created by Administrator on 2016/9/2.
 */
public class Information {
    private String account;
    private String rate;//等级
    private String auth; //是否认证 true false
    private String realName; //真是姓名
    private String identityCard;//身份证号

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityCar() {
        return identityCard;
    }

    public void setIdentityCar(String identityCar) {
        this.identityCard = identityCar;
    }
}
