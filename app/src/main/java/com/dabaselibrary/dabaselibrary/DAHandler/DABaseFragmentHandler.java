package com.dabaselibrary.dabaselibrary.DAHandler;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by DA on 2017/08/03.
 */

public abstract class DABaseFragmentHandler extends Handler{
    private final WeakReference<Fragment> fragmentWeakReference;
    public DABaseFragmentHandler(Fragment fragment) {
        fragmentWeakReference = new WeakReference<>(fragment);
    }
    public void handlerMsg(int what){this.sendEmptyMessage(what);}
    public void handlerMsg(int what,Object object){
        this.sendMessage(this.obtainMessage(what,object));
    }
    @Override
    public void handleMessage(Message msg) {
        Fragment fragment = fragmentWeakReference.get();
        if (fragment != null) {
            handleMessage(fragment,msg);
        }
    }
    protected abstract void handleMessage(Fragment fragment,Message message);
}
