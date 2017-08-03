package com.dabaselibrary.dabaselibrary.DAHandler;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;

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
        DialogFragment dialogFragment = dialogFragmentWeakReference.get();
        if (dialogFragment != null) {
            handleMessage(dialogFragment,msg);
        }
    }
    protected abstract void handleMessage(DialogFragment dialogFragment,Message message);
}
