package com.dabaselibrary.dabaselibrary.DAController;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.dabaselibrary.dabaselibrary.DAControllerInterface.DABaseControllerInterface;
import com.dabaselibrary.dabaselibrary.DAInterface.DAGetResultObject;
import com.dabaselibrary.dabaselibrary.DAInterface.DAHttpDownRespon;
import com.dabaselibrary.dabaselibrary.DAInterface.DAHttpUpRespon;
import com.dabaselibrary.dabaselibrary.DAUtils.OkHttpUtils;
import com.dabaselibrary.dabaselibrary.DAUtils.ThreadUtils;

import java.io.File;
import java.net.CookieStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by DA on 2017/03/13.
 */

public class DABaseController implements DABaseControllerInterface {
    private Context context;
    private OkHttpUtils okHttpUtils;
    private ThreadUtils threadUtils;
    public DABaseController(Context context){
        this.context=context;
        okHttpUtils=OkHttpUtils.getOkHttpUtils(context);
        threadUtils=ThreadUtils.getThreadUtils();
    }
    @Override
    public void showTost(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    //-----------------------------------------------------------------------
    //自定义OkHttpClient
    public void setOkHttpClient(OkHttpClient okHttpClient){
        if(okHttpClient!=null){
            okHttpUtils.setOkHttpClient(okHttpClient);
        }
    }
    //自定义超时时间
    public void setOkHttpTimeOut(int timeOut){
        if(timeOut<300){
            timeOut=300;
        }
        okHttpUtils.setOkHttpTime(timeOut);
    }
    //OkHttp添加cookie
    public void setOkCookie(CookieStore cookieStore){
        if(cookieStore==null) {
            okHttpUtils.setCookie(context);
        }else{
            okHttpUtils.setCookie(cookieStore);
        }
    }
    //获取okHttpClient
    public OkHttpClient getOkHttpClient(){
        return okHttpUtils.okHttpClient;
    }
    //get联网
    @Override
    public String getHttp(String url) throws Exception {
        if(TextUtils.isEmpty(url)){
            throw new Exception("网址为空");
        }
        return okHttpUtils.getHttp(url);
    }
    //需要传入RequestBody对象,post联网
    @Override
    public String postHttp(String url, RequestBody requestBody) throws Exception {
        if(TextUtils.isEmpty(url)){
            throw new Exception("网址为空");
        }
        if(requestBody==null){
            throw new Exception("requestBody is null");
        }
        return okHttpUtils.postHttp(url, requestBody);
    }
    //post提交json数据
    public String postJson(String url,String json) throws Exception {
        if(TextUtils.isEmpty(url)){
            throw new Exception("网址为空");
        }
        if(TextUtils.isEmpty(json)){
            throw new Exception("数据为空");
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return postHttp(url, requestBody);
    }
    //post提交一个键值对
    public String postKeyValue(String url, String key,String value)throws Exception{
        if(TextUtils.isEmpty(url)){
            throw new Exception("网址为空");
        }
        if(TextUtils.isEmpty(key) || TextUtils.isEmpty(value)){
            throw new Exception("数据为空");
        }
        RequestBody formBody = new FormBody.Builder().add(key, value)
                .build();
        return postHttp(url,formBody);
    }
    //post提交多个键值对
    public String postManyKeyValue(String url,HashMap<String,String> datas)throws Exception{
        if(datas.size()<=0){
            throw new Exception("文件数据为空");
        }
        Iterator iterator = datas.entrySet().iterator();
        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            builder.addFormDataPart(key,value);
        }
        MultipartBody build = builder.build();
        return okHttpUtils.postHttp(url,build);
    }
    //post提交单个文件
    public void postSingleFileUp(String url, String filePath, File file, DAHttpUpRespon daHttpUpRespon)throws Exception{
        File fileUp;
        if(file==null || !file.exists()) {
            if(TextUtils.isEmpty(filePath)){
                throw new Exception("文件不存在");
            }
            //传入地址值
            fileUp=new File(filePath);


        }else{
            if(file==null || !file.exists()){
                throw new Exception("文件不存在");
            }
            //传入文件
            fileUp=file;
        }
        if(fileUp==null || !fileUp.exists()){
            throw new Exception("文件不存在");
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), fileUp);
        okHttpUtils.postUpHttp(url,requestBody,daHttpUpRespon);
    }
    //post单个文件下载
    public void postSingFileDown(String url,String filePath,DAHttpDownRespon daHttpDownRespon)throws Exception{
        if(TextUtils.isEmpty(url)){
            throw new Exception("网址为空");
        }
        if(TextUtils.isEmpty(filePath)){
            throw new Exception("保存路径为空");
        }
        okHttpUtils.postDownHttp(url, filePath, daHttpDownRespon);
    }
    //post多个文件上传
    public void postManyFileUp(String url, DAHttpUpRespon daHttpUpRespon, HashMap<String,String> files)throws Exception{
        if(files.size()<=0){
            throw new Exception("文件数据为空");
        }
        Iterator iterator = files.entrySet().iterator();
        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            File file=new File(value);
            if(!file.exists()){
                throw new Exception("找不到文件");
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), file);
            builder.addFormDataPart("file",key,requestBody);
        }
        MultipartBody build = builder.build();
        okHttpUtils.postUpHttp(url,build,daHttpUpRespon);
    }
    //简单的图片加载
    public void getBitmap(String url, DAGetResultObject daGetResultObject){
        okHttpUtils.getHttpRetuen(url, daGetResultObject);
    }
    //-----------------------------------------------------------------------

    //子线程
    public void runInThread(Runnable task){
        threadUtils.runInThread(task);
    }
    //UI线程
    public void runInUIThread(Runnable task){threadUtils.runInUIThread(task);}
    //得到手机串码
    public String[] getTelephony() throws NoSuchAlgorithmException {
        String[] a=new String[2];
        TelephonyManager systemService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = systemService.getSubscriberId();
        String imei = systemService.getDeviceId();
        a[0]=imsi;
        a[1]=imei;
        //拼接登陆的url
        return a;
    }
}
