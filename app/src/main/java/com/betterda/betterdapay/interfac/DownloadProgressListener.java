package com.betterda.betterdapay.interfac;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/14
 * 功能说明：
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public interface DownloadProgressListener {
    /**
     *
     * @param bytesRead 下载的长度
     * @param contentLength 总的长度
     * @param done 是否完成
     */
    void update(long bytesRead, long contentLength, boolean done);
}
