package com.betterda.betterdapay.http;

import com.betterda.betterdapay.interfac.DownloadProgressListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 下载文件的api
 * Created by JokAr on 16/7/5.
 */
public class DownloadAPI {
    private static final String TAG = "DownloadAPI";
    private static final int DEFAULT_TIMEOUT = 60;

    private static DownloadService mDownloadService;


    public static DownloadService downloadService(String url ,DownloadProgressInterceptor interceptor) {

            //因为interceptor涉及到界面的更新,所以每次都需要重新创建对象

            OkHttpClient client =  new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .addInterceptor(interceptor)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(url)
                    .build();
            mDownloadService = retrofit.create(DownloadService.class);




        return mDownloadService;
    }
    public static class DownloadProgressInterceptor implements Interceptor {

        private DownloadProgressListener listener;

        public DownloadProgressInterceptor(DownloadProgressListener listener) {
            this.listener = listener;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            return originalResponse.newBuilder()
                    .body(new DownloadProgressResponseBody(originalResponse.body(), listener))
                    .build();
        }
    }

}

 class DownloadProgressResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private DownloadProgressListener progressListener;
    private BufferedSource bufferedSource;

    public DownloadProgressResponseBody(ResponseBody responseBody,
                                        DownloadProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;

                if (null != progressListener) {
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                }
                return bytesRead;
            }
        };

    }
}