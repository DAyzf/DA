package com.dabaselibrary.dabaselibrary.DAHandler;

import android.app.Activity;
import android.os.Message;

import com.dabaselibrary.dabaselibrary.DABase.DABaseFragmentActivity;

/**
 * Created by DA on 2017/07/31.
 */

public class DABaseFragmentActivityHandler extends DAHandler {
    public DABaseFragmentActivityHandler(Activity activity) { super(activity); }
    protected void handleMessage(Activity activity, Message message){
        DABaseFragmentActivity daBaseFragmentActivity =(DABaseFragmentActivity)activity;
        if(daBaseFragmentActivity ==null){return;}
        daBaseFragmentActivity.handlerMessage(message.what,message.obj);
    }
}
