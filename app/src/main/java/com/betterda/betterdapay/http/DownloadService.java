package com.betterda.betterdapay.http;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by JokAr on 16/7/5.
 */
public interface DownloadService {


    /**
     * 下载
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
