package com.betterda.betterdapay.http;

import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.Constants;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/20
 * 功能说明：
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public interface Api {

    /**
     * 四要素验证

     * @return
     */

    @FormUrlEncoded
    @POST("plat.do?api/four/factors/validate")
    Observable<ResponseBody> getAuth(@Field("data ") String data, @Field("appId ") String appId );
}
