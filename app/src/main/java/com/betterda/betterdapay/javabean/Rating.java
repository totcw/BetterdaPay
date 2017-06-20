package com.betterda.betterdapay.javabean;

import java.util.List;

/**
 * 费率的javabean
 * Created by Administrator on 2016/8/2.
 */
public class Rating {
    private String rankName;//当前等级
    private String nextRankName;//下一等级
    private String remarks;//升级条件
    private List<RateDetail> rates;

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getNextRankName() {
        return nextRankName;
    }

    public void setNextRankName(String nextRankName) {
        this.nextRankName = nextRankName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<RateDetail> getRates() {
        return rates;
    }

    public void setRates(List<RateDetail> rates) {
        this.rates = rates;
    }

    public class RateDetail{
        private String introduce;//通道介绍
        private String t1TradeRate;//隔天费率
        private String t0TradeRate;//实时费率
        private String t1DrawFee;//隔天结算笔数
        private String t0DrawFee;//实时结算笔数
        private String tradeQuota;//隔天额度
        private String dayQuota;//实时额度
        private String type;//通道类型

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getT1TradeRate() {
            return t1TradeRate;
        }

        public void setT1TradeRate(String t1TradeRate) {
            this.t1TradeRate = t1TradeRate;
        }

        public String getT0TradeRate() {
            return t0TradeRate;
        }

        public void setT0TradeRate(String t0TradeRate) {
            this.t0TradeRate = t0TradeRate;
        }

        public String getT1DrawFee() {
            return t1DrawFee;
        }

        public void setT1DrawFee(String t1DrawFee) {
            this.t1DrawFee = t1DrawFee;
        }

        public String getT0DrawFee() {
            return t0DrawFee;
        }

        public void setT0DrawFee(String t0DrawFee) {
            this.t0DrawFee = t0DrawFee;
        }

        public String getTradeQuota() {
            return tradeQuota;
        }

        public void setTradeQuota(String tradeQuota) {
            this.tradeQuota = tradeQuota;
        }

        public String getDayQuota() {
            return dayQuota;
        }

        public void setDayQuota(String dayQuota) {
            this.dayQuota = dayQuota;
        }

        @Override
        public String toString() {
            return "RateDetail{" +
                    "introduce='" + introduce + '\'' +
                    ", t1TradeRate='" + t1TradeRate + '\'' +
                    ", t0TradeRate='" + t0TradeRate + '\'' +
                    ", t1DrawFee='" + t1DrawFee + '\'' +
                    ", t0DrawFee='" + t0DrawFee + '\'' +
                    ", tradeQuota='" + tradeQuota + '\'' +
                    ", dayQuota='" + dayQuota + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Rating{" +
                "rankName='" + rankName + '\'' +
                ", nextRankName='" + nextRankName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", rates=" + rates +
                '}';
    }
}
