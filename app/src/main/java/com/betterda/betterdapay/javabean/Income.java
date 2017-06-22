package com.betterda.betterdapay.javabean;

/**
 * 分润明细
 * Created by Administrator on 2016/8/15.
 */


public class Income {
    private String rate;//分润等级
    private String name;//分润姓名
    private String type;//分润类型
    private String amount;//分润金额
    private String sourceAccount;//用户
    private String incomeTime;//时间

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
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(String incomeTime) {
        this.incomeTime = incomeTime;
    }

    @Override
    public String toString() {
        return "Income{" +
                "rate='" + rate + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", amount='" + amount + '\'' +
                ", sourceAccount='" + sourceAccount + '\'' +
                ", incomeTime='" + incomeTime + '\'' +
                '}';
    }
}
