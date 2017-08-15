package com.betterda.betterdapay.javabean;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/25
 * @功能说明： 交易记录
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class TransactionRecord {



    /**
     *@author : lyf
     *@创建日期： 2017/4/25
     *@功能说明：订单号
     */
    private String orderId;
    /**
     *@author : lyf
     *@创建日期： 2017/4/25
     *@功能说明： 金额
     */
    private String txnAmt;
    /**
     *@author : lyf
     *@创建日期： 2017/4/25
     *@功能说明： 时间
     */
    private String orderTime;
    /**
     *@author : lyf
     *@创建日期： 2017/4/25
     *@功能说明： 交易状态
     */
    private String payStatus;
    /**
     *@author : lyf
     *@创建日期： 2017/6/27
     *@功能说明： 类型 :10收款，20付款
     */
    private String paymentType;
    /**
     *@author : lyf
     *@创建日期： 2017/6/27
     *@功能说明： 交易的通道名称, 微信收款
     */
    private String platMerId;

    public String getPlatMerId() {
        return platMerId;
    }

    public void setPlatMerId(String platMerId) {
        this.platMerId = platMerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "orderId='" + orderId + '\'' +
                ", txnAmt='" + txnAmt + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", platMerId='" + platMerId + '\'' +
                '}';
    }
}
