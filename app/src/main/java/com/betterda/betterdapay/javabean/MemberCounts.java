package com.betterda.betterdapay.javabean;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/16
 * 功能说明：会员个数
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class MemberCounts {
    private String count;
    private String mySpreadCount;
    private String twoSpreadCount;
    private String thredSpreadCount;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMySpreadCount() {
        return mySpreadCount;
    }

    public void setMySpreadCount(String mySpreadCount) {
        this.mySpreadCount = mySpreadCount;
    }

    public String getTwoSpreadCount() {
        return twoSpreadCount;
    }

    public void setTwoSpreadCount(String twoSpreadCount) {
        this.twoSpreadCount = twoSpreadCount;
    }

    public String getThredSpreadCount() {
        return thredSpreadCount;
    }

    public void setThredSpreadCount(String thredSpreadCount) {
        this.thredSpreadCount = thredSpreadCount;
    }

    @Override
    public String toString() {
        return "MemberCounts{" +
                "count='" + count + '\'' +
                ", mySpreadCount='" + mySpreadCount + '\'' +
                ", twoSpreadCount='" + twoSpreadCount + '\'' +
                ", thredSpreadCount='" + thredSpreadCount + '\'' +
                '}';
    }
}
