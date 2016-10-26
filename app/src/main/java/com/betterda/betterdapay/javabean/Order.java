package com.betterda.betterdapay.javabean;

/**
 * 账单
 * Created by Administrator on 2016/8/3.
 */
public class Order {
    private String orderNum;//订单号
    private String orderTime;//订单时间
    private String money;//金额
    private String orderType;//订单类型  0 收款 1分润 2提现
    private String balance;//余额
    private String channel;//通道类型
    private String type;//交易类型

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
