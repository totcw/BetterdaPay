package com.betterda.betterdapay.javabean;

/**
 * 钱包的javabean
 * Created by Administrator on 2016/8/31.
 */
public class Wallet {
    private String balance;//余额
    private String todayIncome;//今日分润
    private String totalIncome;//累计分润
    private String todayAmount;//今日收款
    private String totalAmount;//累计收款


    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(String todayIncome) {
        this.todayIncome = todayIncome;
    }

    public String getTodayAmount() {
        return todayAmount;
    }

    public void setTodayAmount(String todayAmount) {
        this.todayAmount = todayAmount;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "balance='" + balance + '\'' +
                ", todayIncome='" + todayIncome + '\'' +
                ", totalIncome='" + totalIncome + '\'' +
                ", todayAmount='" + todayAmount + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                '}';
    }
}
