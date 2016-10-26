package com.betterda.betterdapay.javabean;

/**
 * 银行卡
 * Created by Administrator on 2016/8/16.
 */
public class BankCard {
    private String id;//银行卡ID
    private String cardName; //持卡人姓名
    private String identityCard; //身份证号
    private String bank; //所属银行
    private String cardNum;//卡号
    private String number;//预留手机号码
    private String cardType;//银行卡类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return cardType;
    }

    public void setType(String type) {
        this.cardType = type;
    }
}
