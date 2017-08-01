package com.dabaselibrary.dabaselibrary.DAHandler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.dabaselibrary.dabaselibrary.DABase.DABaseActivity;

import java.lang.ref.WeakReference;

/**
 * Created by DA on 2017/07/31.
 */

public abstract class DAHandler extends Handler {
    private final WeakReference<DABaseActivity> baseActivityWeakReference;
    public DAHandler(DABaseActivity activity) {baseActivityWeakReference = new WeakReference<>(activity);}
    public void handlerMsg(int what){this.sendEmptyMessage(what);}
    public void handlerMsg(int what,Object object){
        this.sendMessage(this.obtainMessage(what,object));
    }
    @Override
    public void handleMessage(Message msg) {
        DABaseActivity activity = baseActivityWeakReference.get();
        if (activity != null) {
            handleMessage(activity,msg);
        }
    }
    protected abstract void handleMessage(Activity activity,Message message);
}
