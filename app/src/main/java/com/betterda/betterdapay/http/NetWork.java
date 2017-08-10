package com.betterda.betterdapay.http;

import android.util.Log;

import com.betterda.betterdapay.BuildConfig;
import com.betterda.betterdapay.application.MyApplication;
import com.betterda.betterdapay.javabean.BaseCallModel;
import com.betterda.betterdapay.util.Constants;
import com.betterda.betterdapay.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 封装网络请求
 * Created by Administrator on 2016/7/29.
 */
public class NetWork {
    private static NetService netService; //封装了请求的接口
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static HashSet<String> cookies = new HashSet<>();

    /**
     * 通过retrofit返回接口的实现类
     *
     * @return
     */
    public static NetService getNetService() {

        /*可以通过自定义Interceptor来实现很多操作,打印日志,缓存,重试,token验证等*/

        if (netService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Url.URL)
                    .client(okHttpClient.newBuilder()
                            .cache(new Cache(new File(MyApplication.getInstance().getCacheDir(), "responses"), 10 * 1024 * 1024)) //创建一个10M的缓存目录
                            .addInterceptor(interceptor) //缓存的拦截器
                            .addInterceptor(new AddCookiesInterceptor())//添加cookie的拦截器
                            .addInterceptor(new ReceivedCookiesInterceptor())//获取cookie的拦截器 ,这2个是当后台需要配置持有的session才需要
                            .build())
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            netService = retrofit.create(NetService.class);
        }

        return netService;
    }

    /**
     * 获取cookie的拦截器
     */
    public static class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }
            }
            return originalResponse;
        }
    }

    /**
     * 添加cookie
     */
    public static class AddCookiesInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            for (String cookie : cookies) {
                builder.addHeader("Cookie", cookie);
                if (BuildConfig.LOG_DEBUG) {
                    System.out.println("Header Cookie:" + cookie);
                }
            }

            return chain.proceed(builder.build());
        }
    }


    /**
     * 缓存定义拦截器
     */
    static Interceptor interceptor = chain -> {
        //设置请求时
        Request request = chain.request();
        //没网强制使用缓存,否则默认是一直使用网络的
        if (!NetworkUtils.isNetAvailable(MyApplication.getInstance())) {
            request = request.newBuilder()
                    //FORCE_CACHE表示强制使用缓存
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        //设置数据返回时,如果request是走的缓存,那么response就来源于缓存,如果request是走的网络,那么response也是来来源网络,且会把数据缓存
        //要设置response的Cache-Control是怕云端的是no_cache,自己设置就可以和request统一
        Response response = chain.proceed(request);
        if (NetworkUtils.isNetAvailable(MyApplication.getInstance())) {
            //有网的时候读接口上的@Headers里的配置，根据配置是否使用缓存
            String cacheControl = request.cacheControl().toString();
            if (BuildConfig.LOG_DEBUG) {
                System.out.println("cacheControl:" + cacheControl);
            }
            response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;

    };

    /**
     * 用Transformers 预处理observable,比如写好observeOn避免每次调用都要写这个 ,然后使用compose连接
     * 这里多一个参数为了确定T
     */

    public static <T> Observable.Transformer<BaseCallModel<T>, BaseCallModel<T>> handleResult(BaseCallModel<T> baseCallModel) {
        return baseCallModelObservable -> baseCallModelObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}
