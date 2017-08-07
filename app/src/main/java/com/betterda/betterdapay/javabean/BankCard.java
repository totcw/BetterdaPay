package com.betterda.betterdapay.javabean;

/**
 * 银行卡
 * Created by Administrator on 2016/8/16.
 */
public class BankCard {
    private String cardId;//银行卡ID
    private String realName; //持卡人姓名
    private String idCard; //身份证号
    private String bankName; //所属银行
    private String bankCard;//卡号
    private String mobile;//预留手机号码
    private String cardType;//银行卡类型


    public String getId() {
        return cardId;
    }

    public void setId(String id) {
        this.cardId = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
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

    @Override
    public String toString() {
        return "BankCard{" +
                "cardId='" + cardId + '\'' +
                ", realName='" + realName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", mobile='" + mobile + '\'' +
                ", cardType='" + cardType + '\'' +
                '}';
    }
}
