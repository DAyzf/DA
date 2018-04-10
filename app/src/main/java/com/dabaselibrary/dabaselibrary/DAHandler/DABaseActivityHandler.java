package com.dabaselibrary.dabaselibrary.DAHandler;

import android.app.Activity;
import android.os.Message;

import com.dabaselibrary.dabaselibrary.DABase.DABaseActivity;

/**
 * Created by DA on 2018/4/10.
 */
public class DABaseActivityHandler extends DAHandler {
    public DABaseActivityHandler(Activity activity) { super(activity); }
    @Override
    protected void handleMessage(Activity activity, Message message) {
        super.handleMessage(activity, message);
        DABaseActivity daBaseActivity=(DABaseActivity)activity;
        if(daBaseActivity==null){return;}
        daBaseActivity.handlerMessage(message.what,message.obj);
    }
}
