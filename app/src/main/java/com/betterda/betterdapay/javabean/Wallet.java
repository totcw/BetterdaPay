package com.betterda.betterdapay.javabean;

/**
 * 钱包的javabean
 * Created by Administrator on 2016/8/31.
 */
public class Wallet {
    private String heapCollection;//累计收款
    private String collection;//余额
    private String heapRating;//累计分润
    private String rating;//可计算的分润

    public String getHeapCollection() {
        return heapCollection;
    }

    public void setHeapCollection(String heapCollection) {
        this.heapCollection = heapCollection;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getHeapRating() {
        return heapRating;
    }

    public void setHeapRating(String heapRating) {
        this.heapRating = heapRating;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
