package com.dabaselibrary.dabaselibrary.DAControllerInterface;

import com.dabaselibrary.dabaselibrary.DAInterface.DAGetResultObject;
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

public interface DAOpenControllerInterface {
    /**
     *      简单的Toast，基本做测试用
     * @param msg
     */
    void showToast(String msg);
    //-----------------------------------------------
    /**
     *      设置cookie
     * @param cookieStore
     *      自定义实现CookieStore接口的类变量,当为Null时，将会绑定框架原生cookiestore,不具备本地化保存
     */
    void setOkCookie(CookieStore cookieStore);
    /**
     *      自定义okHttpClient
     * @param okHttpClient
     *      可以自己定义自己的okHttpClient
     */
    void setOkHttpClient(OkHttpClient okHttpClient);
    /**
     *      自定义超时时间
     * @param timeOut
     *      可以设置超时时间
     */
    void setOkHttpTimeOut(int timeOut);
    /**
     *      获取OkHttpClient
     * @return
     *      返回设置好的okHttpClient
     */
    OkHttpClient getOkHttpClient();
    /**
     *      简单get联网
     * @param url
     *      需要提交的网址
     * @param getResult
     *      接口回调,String类型
     */
    void getHttp(String url, DAGetResultObject getResult);
    /**
     *      自定义的post链接
     * @param url
     *      需要提交的网址
     * @param requestBody
     *      okhttp封装的Body,无需接入jar包，可以直接创建使用
     *      不会操作的可以上这个网页查看 http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0106/2275.html
     * @param getResult
     *      接口回调,string类型
     */
    void postHttp(String url, RequestBody requestBody, DAGetResultObject getResult);
    /**
     *      post提交json
     * @param url
     *       需要提交的网址
     * @param json
     *      需要提交的json数据
     * @param getResult
     *      接口回调,string类型
     */
    void postJson(String url, String json, DAGetResultObject getResult);
    /**
     *      post提交一个键值对
     * @param url
     *      需要提交的网址
     * @param key
     *      需要上传的key名
     * @param value
     *      需要上传的数据
     * @param getResult
     *      接口回调,string类型
     */
    void postKeyValue(String url, String key, String value, DAGetResultObject getResult);
    /**
     *       post提交多个键值对
     * @param url
     *      需要提交的网址
     * @param datas
     *      HaspMap的数据，key-value对应上传后的key-value
     * @param getResult
     *       接口回调,string类型
     */
    void postManyKeyValue(String url, HashMap<String, String> datas, DAGetResultObject getResult);
    /**
     *      单个文件上传，文件本地路径和文件二选一传入即可，另一个可以传入Null
     * @param url
     *      需要提交的网址
     * @param filePath
     *      文件的本地路径
     * @param file
     *       文件
     * @param daHttpUpRespon
     *       上传进度回调
     */
    void postSingleFileUp(String url, String filePath, File file, DAHttpUpRespon daHttpUpRespon);
    /**
     *      文件下载，不带有断点下载
     * @param url
     *      需要下载的网址
     * @param filePath
     *      保存文件的路径
     * @param daHttpDownRespon
     *      文件下载的进度回调
     */
    void postSingFileDown(String url, String filePath, DAHttpDownRespon daHttpDownRespon);
    /**
     *      多文件上传
     * @param url
     *      需要上传的网址
     * @param daHttpUpRespon
     *      上传的进度回调
     * @param files
     *      文件的数据，key-value形式，key为提交的key名，value为文件路径
     */
    void postManyFileUp(final String url, final DAHttpUpRespon daHttpUpRespon, final HashMap<String, String> files);
    /**
     *      简单的图片加载
     * @param url
     *      图片的网址
     * @param daGetResultObject
     *      图片的回调 返回Bitmap
     */
    void getBitmap(String url, DAGetResultObject daGetResultObject);
    //返回总Utils
    DAOpenUtils getUtils();
    //------------------------------------------------------------------
    /**
     *      运行子线程
     * @param task
     */
    void runInThread(Runnable task);
    /**
     *      运行UI线程
     * @param task
     */
    void runInUIThread(Runnable task);
    /**
     *      获取手机串码
     * @return
     *      string[0]=imsi,string[1]=imei
     * @throws NoSuchAlgorithmException
     *      抛出异常
     */
    String[] getTelephony() throws NoSuchAlgorithmException;
}
