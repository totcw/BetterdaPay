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
    private String oderNum;
    /**
     *@author : lyf
     *@创建日期： 2017/4/25
     *@功能说明： 金额
     */
    private String money;
    /**
     *@author : lyf
     *@创建日期： 2017/4/25
     *@功能说明： 时间
     */
    private String time;
    /**
     *@author : lyf
     *@创建日期： 2017/4/25
     *@功能说明： 交易状态
     */
    private String status;

    public String getOderNum() {
        return oderNum;
    }

    public void setOderNum(String oderNum) {
        this.oderNum = oderNum;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "oderNum='" + oderNum + '\'' +
                ", money='" + money + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
