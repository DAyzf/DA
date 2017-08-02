package com.dabaselibrary.dabaselibrary.DAController;

import android.content.Context;

import com.dabaselibrary.dabaselibrary.DAControllerInterface.DABaseControllerInterface;
import com.dabaselibrary.dabaselibrary.DAInterface.DAGetResultObject;
import com.dabaselibrary.dabaselibrary.DAFactory.DAControllerFactory;
import com.dabaselibrary.dabaselibrary.DAInterface.DAHttpDownRespon;
import com.dabaselibrary.dabaselibrary.DAInterface.DAHttpUpRespon;
import com.dabaselibrary.dabaselibrary.DAOpenUtils.DAOpenUtils;

import java.io.File;
import java.net.CookieStore;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by DA on 2017/03/13.
 */

public class DAOpenController implements com.dabaselibrary.dabaselibrary.DAControllerInterface.DAOpenControllerInterface {
    private DABaseControllerInterface daBaseControllerInterface;
    private DAOpenUtils daOpenUtils;
    private Context context;
    public DAOpenController(Context context){
        daBaseControllerInterface= DAControllerFactory.getBaseController(context);
        this.context=context;
        this.daOpenUtils=DAOpenUtils.getObject(context);
    }
    //弹土司
    @Override
    public void showToast(String msg) {daBaseControllerInterface.showTost(msg);}
    //-----------------------------------------------------------------------
    //设置cookie
    @Override
    public void setOkCookie(CookieStore cookieStore) {
        daBaseControllerInterface.setOkCookie(cookieStore);
    }
    //自定义okHttpClient
    public void setOkHttpClient(OkHttpClient okHttpClient){
        daBaseControllerInterface.setOkHttpClient(okHttpClient);
    }
    //设置超时时间
    public void setOkHttpTimeOut(int timeOut){
        daBaseControllerInterface.setOkHttpTimeOut(timeOut);
    }
    //获取OkHttpClient
    public OkHttpClient getOkHttpClient(){
        return daBaseControllerInterface.getOkHttpClient();
    }
    //get联网
    @Override
    public void getHttp(final String url, final DAGetResultObject getResult) {
        runInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getResult.getResult(daBaseControllerInterface.getHttp(url));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //post联网，自定义RequestBody
    @Override
    public void postHttp(final String url, final RequestBody requestBody, final DAGetResultObject getResult) {
        runInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getResult.getResult(daBaseControllerInterface.postHttp(url, requestBody));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //post提交json数据
    public void postJson(final String url, final String json,final DAGetResultObject getResult){
        runInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getResult.getResult(daBaseControllerInterface.postJson(url, json));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //post提交一个键值对
    public void postKeyValue(final String url, final String key, final String value, final DAGetResultObject getResult){
        runInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getResult.getResult(daBaseControllerInterface.postKeyValue(url, key, value));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //post提交多个键值对
    public void postManyKeyValue(final String url, final HashMap<String,String> datas, final DAGetResultObject getResult){
        runInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getResult.getResult(daBaseControllerInterface.postManyKeyValue(url, datas));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //post单个文件上传
    public void postSingleFileUp(final String url, final String filePath, final File file, final DAHttpUpRespon daHttpUpRespon){
        runInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    daBaseControllerInterface.postSingleFileUp(url, filePath, file, daHttpUpRespon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //post单个文件下载
    public void postSingFileDown(final String url,final String filePath,final DAHttpDownRespon daHttpDownRespon){
        runInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    daBaseControllerInterface.postSingFileDown(url, filePath, daHttpDownRespon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //post多文件上传
    public void postManyFileUp(final String url,final DAHttpUpRespon daHttpUpRespon,final HashMap<String,String> files){
        runInThread(new Runnable() {
            @Override
            public void run() {
                try {
                    daBaseControllerInterface.postManyFileUp(url, daHttpUpRespon, files);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //简单的图片加载
    public void getBitmap(String url, DAGetResultObject daGetResultObject){
        daBaseControllerInterface.getBitmap(url, daGetResultObject);
    }
    //返回Utils
    @Override
    public DAOpenUtils getUtils() {return daOpenUtils;}
    //-----------------------------------------------------------------------

    //子线程
    @Override
    public void runInThread(Runnable task) {
        daBaseControllerInterface.runInThread(task);
    }
    //UI线程
    @Override
    public void runInUIThread(Runnable task) {
        daBaseControllerInterface.runInUIThread(task);
    }

    //得到手机串码
    @Override
    public String[] getTelephony() throws NoSuchAlgorithmException {
        return daBaseControllerInterface.getTelephony();
    }

}
