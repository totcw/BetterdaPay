package com.betterda.betterdapay.javabean;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/4/25
 * @功能说明： 办理信用卡的卡种推荐
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public class CreditType {
    private String url; //
    private String bankName; //银行名字
    private String introduce; //介绍
    private String bankRate; //银行卡等级
    private String number; //人数

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getBankRate() {
        return bankRate;
    }

    public void setBankRate(String bankRate) {
        this.bankRate = bankRate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "CreditType{" +
                "url='" + url + '\'' +
                ", bankName='" + bankName + '\'' +
                ", introduce='" + introduce + '\'' +
                ", bankRate='" + bankRate + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
