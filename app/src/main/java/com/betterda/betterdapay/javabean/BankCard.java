package com.betterda.betterdapay.javabean;

/**
 * 银行卡
 * Created by Administrator on 2016/8/16.
 */
public class BankCard {
    private String cardId;//银行卡ID
    private String realname; //持卡人姓名
    private String idcard; //身份证号
    private String bankname; //所属银行
    private String bankcard;//卡号
    private String mobile;//预留手机号码
    private String cardType;//银行卡类型


    public String getId() {
        return cardId;
    }

    public void setId(String id) {
        this.cardId = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return cardType;
    }

    public void setType(String type) {
        this.cardType = type;
    }
}
