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
        Fragment fragment = fragmentWeakReference.get();
        if (fragment != null) {
            handleMessage(fragment,msg);
        }
    }
    protected abstract void handleMessage(Fragment fragment,Message message);
}
