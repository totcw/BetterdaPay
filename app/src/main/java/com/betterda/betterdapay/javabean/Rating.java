package com.betterda.betterdapay.javabean;

import java.util.List;

/**
 * 费率的javabean
 * Created by Administrator on 2016/8/2.
 */
public class Rating {
    private String rankName;//当前等级
    private String nextRankName;//下一等级
    private String upExplain;//升级条件
    private List<RateDetail> walletMerberChannelRates;

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

    public String getUpExplain() {
        return upExplain;
    }

    public void setUpExplain(String upExplain) {
        this.upExplain = upExplain;
    }

    public List<RateDetail> getWalletMerberChannelRates() {
        return walletMerberChannelRates;
    }

    public void setWalletMerberChannelRates(List<RateDetail> walletMerberChannelRates) {
        this.walletMerberChannelRates = walletMerberChannelRates;
    }

    public class RateDetail{
        private String introduce;//通道介绍
        private String typeName;//渠道类型名称
        private String type;//区分 T+0和T+1
        private String tradeRate;//费率
        private String tradeQuota;//单笔额度
        private String dayQuota;//当天额度
        private String leastTradeRate;//最小手续费
        private String typeCode;//每条渠道对应的唯一值
        private String channelId;//通道id

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTradeRate() {
            return tradeRate;
        }

        public void setTradeRate(String tradeRate) {
            this.tradeRate = tradeRate;
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

        public String getLeastTradeRate() {
            return leastTradeRate;
        }

        public void setLeastTradeRate(String leastTradeRate) {
            this.leastTradeRate = leastTradeRate;
        }

        @Override
        public String toString() {
            return "RateDetail{" +
                    "introduce='" + introduce + '\'' +
                    ", typeName='" + typeName + '\'' +
                    ", type='" + type + '\'' +
                    ", tradeRate='" + tradeRate + '\'' +
                    ", tradeQuota='" + tradeQuota + '\'' +
                    ", dayQuota='" + dayQuota + '\'' +
                    ", leastTradeRate='" + leastTradeRate + '\'' +
                    ", typeCode='" + typeCode + '\'' +
                    ", channelId='" + channelId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Rating{" +
                "rankName='" + rankName + '\'' +
                ", nextRankName='" + nextRankName + '\'' +
                ", upExplain='" + upExplain + '\'' +
                ", walletMerberChannelRates=" + walletMerberChannelRates +
                '}';
    }
}
