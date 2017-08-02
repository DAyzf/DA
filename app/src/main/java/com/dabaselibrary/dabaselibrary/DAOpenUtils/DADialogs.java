package com.dabaselibrary.dabaselibrary.DAOpenUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.dabaselibrary.dabaselibrary.R;

/**
 * Created by DA on 2017/07/16.
 * Dialog封装类
 */

public class DADialogs {
    //Dialog进度条
    public ProgressDialog progressBar(Activity activity){
        return progressBar(activity,"提醒","正在加载中,请稍后...");}
    public ProgressDialog progressBar(Activity activity,String title,String message){
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        return dialog;
    }
    //通知对话框
    public AlertDialog showNotifyDialog(Activity activity,String title,String msg,String btn1,String btn2,
                                 DialogInterface.OnClickListener onClickListener1,
                                 DialogInterface.OnClickListener onClickListener2,
                                 boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(btn1,onClickListener1);
        builder.setNegativeButton(btn2, onClickListener2);
        builder.setCancelable(isCancelable);
        return builder.create();
    }
    //列表对话框
    public AlertDialog showListDialog(Activity activity,String title,String[] strings,
                               DialogInterface.OnClickListener OnClickListener
                                ,boolean isCancelable,int mipmapId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        if(mipmapId!=0){
            builder.setIcon(mipmapId);/*设置图标*/
        }
        builder.setItems(strings,OnClickListener);
        builder.setCancelable(isCancelable);
        return builder.create();
    }
    //显示单选对话框
    public AlertDialog showMultipleChoiceDialog(Activity activity,String title,int mipmapId,
                                         String[] strings,int checkedItem,
                                         DialogInterface.OnClickListener singleChoiceListener,
                                         String button1T,
                                         DialogInterface.OnClickListener button1Listener,
                                         String button2T,
                                         DialogInterface.OnClickListener button2Listener,
                                         boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        if(mipmapId!=0) {
            builder.setIcon(mipmapId);
        }
        builder.setSingleChoiceItems(strings, checkedItem, singleChoiceListener);
        builder.setPositiveButton(button1T, button1Listener);
        builder.setNegativeButton(button2T, button2Listener);
        builder.setCancelable(isCancelable);
        return builder.create();
    }
    //显示多选对话框
    public AlertDialog showMultiChoiceDialog(Activity activity,String title,int mipmapId,
                                      String[] strings,boolean[] checkedItems,
                                      DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener,
                                      String button1T,
                                      DialogInterface.OnClickListener button1Listener,
                                      String button2T,
                                      DialogInterface.OnClickListener button2Listener,
                                      boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        if(mipmapId!=0) {
            builder.setIcon(mipmapId);
        }
        builder.setMultiChoiceItems(strings, checkedItems, onMultiChoiceClickListener);
        builder.setPositiveButton(button1T, button1Listener);
        builder.setNegativeButton(button2T, button2Listener);
        builder.setCancelable(isCancelable);
        return builder.create();
    }
}
