package com.betterda.betterdapay.javabean;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/8/10
 * 功能说明： 获取分享信息, 二维码,邀请码
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class ShareInfo {
    private String userType; //10 代理商  20商户
    private String inviteCode; //邀请码
    private String userInviteUrl; //注册url

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getUserInviteUrl() {
        return userInviteUrl;
    }

    public void setUserInviteUrl(String userInviteUrl) {
        this.userInviteUrl = userInviteUrl;
    }

    @Override
    public String toString() {
        return "ShareInfo{" +
                "userType='" + userType + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", userInviteUrl='" + userInviteUrl + '\'' +
                '}';
    }
}
