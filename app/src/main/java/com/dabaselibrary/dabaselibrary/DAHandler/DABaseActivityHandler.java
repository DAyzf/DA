package com.dabaselibrary.dabaselibrary.DAHandler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by DA on 2017/07/31.
 */

public abstract class DABaseActivityHandler extends Handler {
    private final WeakReference<Activity> activityWeakReference;
    public DABaseActivityHandler(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }
    public void handlerMsg(int what){this.sendEmptyMessage(what);}
    public void handlerMsg(int what,Object object){
        this.sendMessage(this.obtainMessage(what,object));
    }
    @Override
    public void handleMessage(Message msg) {
        Activity activity = activityWeakReference.get();
        if (activity != null) {
            handleMessage(activity,msg);
        }
    }
    protected abstract void handleMessage(Activity activity,Message message);
}
