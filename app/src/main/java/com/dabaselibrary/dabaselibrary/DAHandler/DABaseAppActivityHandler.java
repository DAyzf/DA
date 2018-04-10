package com.dabaselibrary.dabaselibrary.DAHandler;

import android.app.Activity;
import android.os.Message;

import com.dabaselibrary.dabaselibrary.DABase.DABaseAppActivity;

/**
 * Created by DA on 2018/4/10.
 */
public class DABaseAppActivityHandler extends DAHandler {
    public DABaseAppActivityHandler(Activity activity) { super(activity); }
    protected void handleMessage(Activity activity, Message message){
       DABaseAppActivity appActivity = (DABaseAppActivity)activity;
       if(appActivity==null){return;}
       appActivity.handlerMessage(message.what,message.obj);
    }
}
