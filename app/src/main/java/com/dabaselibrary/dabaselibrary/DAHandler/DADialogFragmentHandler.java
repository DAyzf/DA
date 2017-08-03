package com.dabaselibrary.dabaselibrary.DAHandler;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;


/**
 * Created by DA on 2017/08/03.
 */

public abstract class DADialogFragmentHandler extends Handler{
    private final WeakReference<DialogFragment> dialogFragmentWeakReference;
    public DADialogFragmentHandler(DialogFragment dialogFragment) {
        dialogFragmentWeakReference = new WeakReference<>(dialogFragment);
    }
    public void handlerMsg(int what){this.sendEmptyMessage(what);}
    public void handlerMsg(int what,Object object){
        this.sendMessage(this.obtainMessage(what,object));
    }
    @Override
    public void handleMessage(Message msg) {
        DialogFragment fragment = dialogFragmentWeakReference.get();
        if (fragment != null) {
            handleMessage(fragment,msg);
        }
    }
    protected abstract void handleMessage(Fragment fragment,Message message);
}
