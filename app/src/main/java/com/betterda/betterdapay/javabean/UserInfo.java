package com.betterda.betterdapay.javabean;

/**
 * 用户信息
 * Created by Administrator on 2016/7/29.
 */
public class UserInfo {

    private String account;//账号
    private String rate;//当前等级
    private String role;//当前等级对应的数
    private String token;//token
    private boolean auth;//是否实名认证
    private String cardNo;//认证人的身份证号码
    private String trueName;//认证姓名

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
}
