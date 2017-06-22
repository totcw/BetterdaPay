package com.betterda.betterdapay.javabean;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/22
 * 功能说明：结算
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class WithDraw {
    private String amount;
    private String status;
    private String disburseTime;
    private String statusName;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisburseTime() {
        return disburseTime;
    }

    public void setDisburseTime(String disburseTime) {
        this.disburseTime = disburseTime;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "WithDraw{" +
                "amount='" + amount + '\'' +
                ", status='" + status + '\'' +
                ", disburseTime='" + disburseTime + '\'' +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
