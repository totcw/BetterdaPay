package com.betterda.betterdapay.javabean;

/**
 * 定义一个网络请求的回调基类
 * Created by Administrator on 2016/7/27.
 */
public class BaseCallModel<T> {
    private int  code;
    private String resultMsg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
