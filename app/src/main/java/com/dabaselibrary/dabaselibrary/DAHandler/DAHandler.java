package com.dabaselibrary.dabaselibrary.DAHandler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by DA on 2018/4/10.
 */
public class DAHandler extends Handler {
    private final WeakReference<Activity> activityWeakReference;
    public DAHandler(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }
    public void handlerMsg(int what){this.sendEmptyMessage(what);}
    public void handlerMsg(int what,Object object){
        this.sendMessage(this.obtainMessage(what,object));}
    //多条what
    public void handlerMoreMsg(int... what){
        for (int i:what) {
            this.handlerMsg(i);
        }
    }
    //多条带object的数据
    public void handlerMsg(Object[] objects,int... what){
        for (int i=0;i<objects.length;i++) {
            if(i<what.length) {
                handlerMsg(what[i], objects[i]);
            }
        }
        if(what.length>objects.length){
            for (int j=objects.length;j<what.length;j++){
                handlerMsg(what[j]);
            }
        }
    }
    @Override
    public void handleMessage(Message msg) {
        Activity activity = activityWeakReference.get();
        if (activity != null) {
            handleMessage(activity,msg);
        }
    }
    protected void handleMessage(Activity activity,Message message){};
}
