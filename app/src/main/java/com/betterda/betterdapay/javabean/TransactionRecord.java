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
    private String orderNo;
    /**
     *@author : lyf
     *@创建日期： 2017/4/25
     *@功能说明： 金额
     */
    private String amount;
    /**
     *@author : lyf
     *@创建日期： 2017/4/25
     *@功能说明： 时间
     */
    private String txnTime;
    /**
     *@author : lyf
     *@创建日期： 2017/4/25
     *@功能说明： 交易状态
     */
    private String status;
    /**
     *@author : lyf
     *@创建日期： 2017/6/27
     *@功能说明： 类型 :1收款，2付款
     */
    private String type;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "orderNo='" + orderNo + '\'' +
                ", amount='" + amount + '\'' +
                ", txnTime='" + txnTime + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
