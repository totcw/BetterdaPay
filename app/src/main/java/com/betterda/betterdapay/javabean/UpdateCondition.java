package com.betterda.betterdapay.javabean;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/19
 * 功能说明：升级条件
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class UpdateCondition {
    private String rate;
    private String rating; //费率
    private String condition;//升级条件
    private String award;//奖励加成
    private String payUp;//金额

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }


    public String getPayUp() {
        return payUp;
    }

    public void setPayUp(String payUp) {
        this.payUp = payUp;
    }

    @Override
    public String toString() {
        return "UpdateCondition{" +
                "rate='" + rate + '\'' +
                ", rating='" + rating + '\'' +
                ", condition='" + condition + '\'' +
                ", award='" + award + '\'' +
                ", payUp='" + payUp + '\'' +
                '}';
    }
}
