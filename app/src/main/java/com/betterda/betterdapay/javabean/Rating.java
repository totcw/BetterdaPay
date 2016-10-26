package com.betterda.betterdapay.javabean;

import java.util.List;

/**
 * 费率的javabean
 * Created by Administrator on 2016/8/2.
 */
public class Rating {
    private String rate;//当前等级
    private String nextRate;//下一等级
    private String conditions;//升级条件
    private List<RateDetail> rateDetail;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getNextRate() {
        return nextRate;
    }

    public void setNextRate(String nextRate) {
        this.nextRate = nextRate;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public List<RateDetail> getRateDetail() {
        return rateDetail;
    }

    public void setRateDetail(List<RateDetail> rateDetail) {
        this.rateDetail = rateDetail;
    }

    public class RateDetail{
        private String intoduce;//通道介绍
        private String nextrating;//隔天费率
        private String realrating;//实时费率
        private String nextaccounts;//隔天结算笔数
        private String realaccounts;//实时结算笔数
        private String nextlimit;//隔天额度
        private String reallime;//实时额度
        private String type;//通道类型

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIntoduce() {
            return intoduce;
        }

        public void setIntoduce(String intoduce) {
            this.intoduce = intoduce;
        }

        public String getNextrating() {
            return nextrating;
        }

        public void setNextrating(String nextrating) {
            this.nextrating = nextrating;
        }

        public String getRealrating() {
            return realrating;
        }

        public void setRealrating(String realrating) {
            this.realrating = realrating;
        }

        public String getNextaccounts() {
            return nextaccounts;
        }

        public void setNextaccounts(String nextaccounts) {
            this.nextaccounts = nextaccounts;
        }

        public String getRealaccounts() {
            return realaccounts;
        }

        public void setRealaccounts(String realaccounts) {
            this.realaccounts = realaccounts;
        }

        public String getNextlimit() {
            return nextlimit;
        }

        public void setNextlimit(String nextlimit) {
            this.nextlimit = nextlimit;
        }

        public String getReallime() {
            return reallime;
        }

        public void setReallime(String reallime) {
            this.reallime = reallime;
        }
    }
}
