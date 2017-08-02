package com.dabaselibrary.dabaselibrary.DAOpenUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.SystemClock;

import java.io.File;

/**
 * Created by DA on 2017/04/10.
 * 下载更新进度条
 */

public class DADownFileUtils {
    private Context context;
    private NotificationManager notificationManager;
    private int notificationManagerInt=19172440;
    private Notification.Builder builder;
    private int lastProgress=0;
    DADownFileUtils(Context context){
        this.context=context;
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
    public void createDownloadProgress(int mipmapId){
        createDownloadProgress(mipmapId,"开始下载","正在下载中....");}
    //下载进度条
    public void createDownloadProgress(int mipmapId, String title, String contentText){
        if(mipmapId==0){
            builder = new Notification.Builder(context).setTicker(title)
                    .setContentText(contentText)
                    .setProgress(100, 0, false);
        }else {
            builder = new Notification.Builder(context).setLargeIcon(
                    BitmapFactory.decodeResource(context.getResources(), mipmapId))
                    .setSmallIcon(mipmapId).setTicker(title)
                    .setContentText(contentText)
                    .setProgress(100, 0, false);
        }
        Notification build = builder.build();
        build.flags=Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(notificationManagerInt, build);
    }
    //更新进度条
    public void updateDownloadProgress(int progress){
        if(progress<=lastProgress){
            return;
        }
        lastProgress=progress;
        builder.setProgress(100,progress,false);
        builder.setContentText("正在下载"+progress+"%....");
        Notification build = builder.build();
        build.flags=Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(notificationManagerInt,build);
    }
    //安装
    public void install(String apkPath){
        lastProgress=0;
        builder.setProgress(100,100,true);
        builder.setContentText("正在安装....");
        Notification build = builder.build();
        build.flags=Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(notificationManagerInt,build);
        SystemClock.sleep(1000);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        SystemClock.sleep(3000);
        notificationManager.cancel(notificationManagerInt);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
