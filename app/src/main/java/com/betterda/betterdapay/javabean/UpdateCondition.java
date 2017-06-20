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
    private String rankName;//等级名字
    private String rankId; //等级id
    private String rating; //费率
    private String remarks;//升级条件
    private String award;//奖励加成
    private String payUp;//金额

    public String getRankId() {
        return rankId;
    }

    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
                "rankName='" + rankName + '\'' +
                ", rankId='" + rankId + '\'' +
                ", rating='" + rating + '\'' +
                ", remarks='" + remarks + '\'' +
                ", award='" + award + '\'' +
                ", payUp='" + payUp + '\'' +
                '}';
    }
}