package com.dabaselibrary.dabaselibrary.DAUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dabaselibrary.dabaselibrary.DACookie.PersistentCookieStore;
import com.dabaselibrary.dabaselibrary.DAInterface.DAGetResultObject;
import com.dabaselibrary.dabaselibrary.DAInterface.DAHttpDownRespon;
import com.dabaselibrary.dabaselibrary.DAInterface.DAHttpUpRespon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;


/**
 * Created by DA on 2016/12/13.
 * OkHttp工具类
 */

public class OkHttpUtils {
    public OkHttpClient okHttpClient;
    private static  OkHttpUtils okHttpUtils;
    private CookieHandler cookieHandler;
    private int timeOut=300;
    public static OkHttpUtils getOkHttpUtils(Context context){
        if(okHttpUtils==null){
            okHttpUtils=new OkHttpUtils(context);
        }
        return  okHttpUtils;
    }
    private OkHttpUtils(Context context){
        okHttpClient=new OkHttpClient();
    }
    public void setCookie(Context context){
        cookieHandler = new CookieManager(
                new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .connectTimeout(timeOut, TimeUnit.SECONDS).build();
    }
    public void setCookie(CookieStore cookieStore){
        cookieHandler = new CookieManager(
                cookieStore, CookiePolicy.ACCEPT_ALL);
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .connectTimeout(timeOut, TimeUnit.SECONDS).build();
    }
    public void setOkHttpClient(OkHttpClient okHttpClient){
        this.okHttpClient=okHttpClient;
    }
    public void setOkHttpTime(int timeOut){
        this.timeOut=timeOut;
        if(cookieHandler!=null){
            okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new JavaNetCookieJar(cookieHandler))
                    .connectTimeout(timeOut, TimeUnit.SECONDS).build();
        }else{
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(timeOut, TimeUnit.SECONDS).build();
        }
    }
    //get联网
    public String getHttp(String url) throws Exception {
        Request request=new Request.Builder().url(url).build();
        Response response=okHttpClient.newCall(request).execute();
        return treatedResponse(response);
    }
    //get图片回调
    public void getHttpRetuen(String url, final DAGetResultObject daGetResultObject){
        Request request=new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("aa",e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                daGetResultObject.getResult(bmp);
            }
        });
    }
    //POST联网
    public String postHttp(String url, RequestBody requestBody)throws Exception{
        Request request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response=okHttpClient.newCall(request).execute();
        return treatedResponse(response);
    }

    //POST下载
    public void postDownHttp(String url,final String downFilePath, DAHttpDownRespon daHttpDownRespon)throws Exception{
        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new LoggingInterceptor(daHttpDownRespon)).build();
        //封装请求
        Request request = new Request.Builder()
                //下载地址
                .url(url)
                .build();
        defaultHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("failure");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                int len;
                byte[] buf = new byte[2048];
                InputStream inputStream = response.body().byteStream();
                //可以在这里自定义路径
                File file1 = new File(downFilePath);
                if(file1.isFile() && file1.exists()){
                    file1.delete();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file1);
                while ((len = inputStream.read(buf)) != -1) {
                    fileOutputStream.write(buf, 0, len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
            }
        });
    }
    //POST提交
    public void postUpHttp(String url, RequestBody requestBody, DAHttpUpRespon daHttpUpRespon)throws Exception{
        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new LoggingInterceptor2(daHttpUpRespon)).build();
        //封装请求
        Request request = new Request.Builder()
                //下载地址
                .url(url)
                .put(requestBody)
                .build();
        defaultHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("failure");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });

    }
    //拦截器
    private class LoggingInterceptor implements Interceptor {
        private DAHttpDownRespon daRespon;
        LoggingInterceptor(DAHttpDownRespon daRespon){
            this.daRespon=daRespon;
        }
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder().body(
                    new ProgressResponseBody(originalResponse.body(), daRespon))
                    .build();
        }
    }
    private class LoggingInterceptor2 implements Interceptor {
        private DAHttpUpRespon daRespon;
        LoggingInterceptor2(DAHttpUpRespon daRespon){
            this.daRespon=daRespon;
        }
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request originalResponse = chain.request();
            Request signedRequest =
                    originalResponse.newBuilder().put(new ProgressRequestBody(originalResponse.body(), daRespon)).build();
            // 在这做所有你需要做的事情，重新产生一个 Request 送出去。
            return chain.proceed(signedRequest);
        }
    }
    //下载进度回调
    private class ProgressResponseBody extends ResponseBody {
        private final ResponseBody responseBody;
        private final DAHttpDownRespon daRespon;
        private BufferedSource bufferedSource;
        public ProgressResponseBody(ResponseBody responseBody, DAHttpDownRespon daRespon) {
            this.responseBody = responseBody;
            this.daRespon = daRespon;
        }
        @Override public MediaType contentType() {
            return responseBody.contentType();
        }
        @Override public long contentLength() {
            return responseBody.contentLength();
        }
        @Override public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }
        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;
                @Override public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    daRespon.onResponseProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }
    //上传进度回调
    public  class ProgressRequestBody extends RequestBody {
        //实际的待包装请求体
        private final RequestBody requestBody;
        //进度回调接口
        private final DAHttpUpRespon daHttpUpRespon;
        //包装完成的BufferedSink
        private BufferedSink bufferedSink;
        public ProgressRequestBody(RequestBody requestBody, DAHttpUpRespon daHttpUpRespon) {
            this.requestBody = requestBody;
            this.daHttpUpRespon = daHttpUpRespon;
        }
        @Override
        public MediaType contentType() {
            return requestBody.contentType();
        }
        @Override
        public long contentLength() throws IOException {
            return requestBody.contentLength();
        }
        @Override
        public void writeTo(@NonNull BufferedSink sink) throws IOException {
            if (bufferedSink == null) {
                bufferedSink = Okio.buffer(sink(sink));
            }
            requestBody.writeTo(bufferedSink);
            bufferedSink.flush();
        }
        private Sink sink(Sink sink) {
            return new ForwardingSink(sink) {
                long bytesWritten = 0L;
                long contentLength = 0L;
                @Override
                public void write(@NonNull Buffer source, long byteCount) throws IOException {
                    super.write(source, byteCount);
                    if (contentLength == 0) {
                        contentLength = contentLength();
                    }
                    bytesWritten += byteCount;
                    daHttpUpRespon.onRequestProgress(bytesWritten, contentLength, bytesWritten == contentLength);
                }
            };
        }
    }
    //处理response方法
    private String treatedResponse(Response response) throws Exception {
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new Exception("出错了");
        }
    }
}
