package com.dabaselibrary.dabaselibrary.DAHandler;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;

import com.dabaselibrary.dabaselibrary.DABase.DABaseDialogFragment;

import java.lang.ref.WeakReference;



/**
 * Created by DA on 2017/08/03.
 */

public class DADialogFragmentHandler extends Handler{
    private final WeakReference<DialogFragment> dialogFragmentWeakReference;
    public DADialogFragmentHandler(DialogFragment dialogFragment) {
        dialogFragmentWeakReference = new WeakReference<>(dialogFragment);
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
        DialogFragment dialogFragment = dialogFragmentWeakReference.get();
        if (dialogFragment != null) {
            handleMessage(dialogFragment,msg);
        }
    }
    protected void handleMessage(DialogFragment dialogFragment,Message message){
        DABaseDialogFragment daBaseDialogFragment=(DABaseDialogFragment)dialogFragment;
        if(daBaseDialogFragment==null){return;}
        daBaseDialogFragment.handlerMessage(message.what,message.obj);
    }
}
