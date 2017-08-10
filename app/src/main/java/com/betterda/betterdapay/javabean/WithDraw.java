package com.betterda.betterdapay.javabean;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/22
 * 功能说明：结算明细
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class WithDraw {
    private String drawCash;//金额
    private String status;//状态
    private String drawTimeStr;//时间
    private String drawResult;

    public String getDrawCash() {
        return drawCash;
    }

    public void setDrawCash(String drawCash) {
        this.drawCash = drawCash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDrawTimeStr() {
        return drawTimeStr;
    }

    public void setDrawTimeStr(String drawTimeStr) {
        this.drawTimeStr = drawTimeStr;
    }

    public String getDrawResult() {
        return drawResult;
    }

    public void setDrawResult(String drawResult) {
        this.drawResult = drawResult;
    }

    @Override
    public String toString() {
        return "WithDraw{" +
                "drawCash='" + drawCash + '\'' +
                ", status='" + status + '\'' +
                ", drawTimeStr='" + drawTimeStr + '\'' +
                ", drawResult='" + drawResult + '\'' +
                '}';
    }
}
