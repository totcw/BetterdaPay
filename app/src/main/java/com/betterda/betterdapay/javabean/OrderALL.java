package com.betterda.betterdapay.javabean;

import java.util.List;

/**
 * 账单记录和累积收款
 * Created by Administrator on 2016/8/19.
 */
public class OrderALL {

    private String  heapCollection;
    private List<Order> listOrder;

    public String getHeapCollection() {
        return heapCollection;
    }

    public void setHeapCollection(String heapCollection) {
        this.heapCollection = heapCollection;
    }

    public List<Order> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<Order> listOrder) {
        this.listOrder = listOrder;
    }
}
