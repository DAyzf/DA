package com.dabaselibrary.dabaselibrary.DABase;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dabaselibrary.dabaselibrary.DAHandler.DABaseFragmentHandler;
import com.dabaselibrary.dabaselibrary.DAInterface.DAPermissionButtonCallback;
import com.dabaselibrary.dabaselibrary.DAInterface.DAPermissionCallback;

/**
 * Created by DA on 2017/03/13.
 */

public abstract class DABaseFragment extends Fragment {
    protected Bundle bundle;
    private DAPermissionCallback daPermissionCallback;
    protected int WRITE_SETTINGS=9;
    protected int SYSTEM_ALERT_WINDOW=10;
    protected DABaseFragmentHandler daHandler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bundle=new Bundle();
        daHandler=new DABaseFragmentHandler(this);
        View view= initView();
        getViewObject(view);
        return view;
    }

    /**
     *      获取控件的方法
     * @param view
     *      初始化方法的view会回传过来，可以view.find拿到控件
     */
    protected void getViewObject(View view) {}
    /**
     *      必须实现的方法,初始化界面
     * @return
     *      View
     */
    protected abstract View initView();

    @Override
    public void onStart() {
        super.onStart();
        initData();
        initEvent();
    }
    /**
     *  点击事件
     */
    protected void initEvent() {}
    /**
     *  初始化数据
     */
    protected void initData() {}

    //Intent
    /**
     *      发送intent
     * @param string
     *      调用系统时传入的系统String
     * @param i
     *      Result回调时的requestCode
     */
    protected void SendIntent(String string,int i){
        Intent intent=new Intent(string);
        if(!bundle.isEmpty()){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,i);
        bundle.clear();
    }
    /**
     *      不回调的activity跳转,如需添加数据，可以直接操作bundle即可
     * @param packageContext
     *      Context
     * @param cls
     *      activity名
     */
    protected void SendIntent(Context packageContext, Class<?> cls){
        SendIntent(packageContext,cls,false,0,0,0);
    }
    /**
     *  没有回调的activity跳转，可以自定义跳转动画
     * @param packageContext
     *      Context
     * @param cls
     *       activity名
     * @param enterAnim
     *      进入的动画效果，不需要可以传入0
     * @param exitAnim
     *      退出的动画效果，不需要可以传入0
     */
    protected void SendIntent(Context packageContext, Class<?> cls,int enterAnim,int exitAnim){
        SendIntent(packageContext, cls,false,0,enterAnim, exitAnim);
    }
    /**
     *   能回调的activity跳转,如需添加数据，可以直接操作bundle即可
     * @param packageContext
     * @param cls
     *    activity名
     * @param isReturen
     *    是否回调 true-回调  false-不回调
     * @param i
     *      Result回调时的requestCode,如果不回调，可以传入0
     * @param enterAnim
     *      进入的动画效果，不需要可以传入0
     * @param exitAnim
     *      退出的动画效果，不需要可以传入0
     */
    protected void SendIntent(Context packageContext, Class<?> cls,boolean isReturen,int i,int enterAnim,int exitAnim){
        Intent intent=new Intent(packageContext,cls);
        if(!bundle.isEmpty()){
            intent.putExtras(bundle);
        }
        if(isReturen){
            startActivityForResult(intent,i);
        }else{
            startActivity(intent);
        }
        if(enterAnim!=0 && exitAnim!=0){
            getActivity().overridePendingTransition(enterAnim,exitAnim);
        }
        bundle.clear();
    }
    /**
     *  自定义跳转，比如apk安装界面,如需添加数据，可以直接操作bundle即可
     * @param intent
     * @param isReturen
     *      是否回调 true-回调  false-不回调
     * @param i
     *      Result回调时的requestCode,如果不回调，可以传入0
     * @param enterAnim
     *      进入的动画效果，不需要可以传入0
     * @param exitAnim
     *      退出的动画效果，不需要可以传入0
     */
    protected void SendIntent(Intent intent,boolean isReturen,int i,int enterAnim,int exitAnim){
        if(!bundle.isEmpty()){
            intent.putExtras(bundle);
        }
        if(isReturen){
            startActivityForResult(intent,i);
        }else{
            startActivity(intent);
        }
        if(enterAnim!=0 && exitAnim!=0){
            getActivity().overridePendingTransition(enterAnim,exitAnim);
        }
        bundle.clear();
    }

    //申请权限
    /**
     *  权限申请方法 SYSTEM_ALERT_WINDOW和WRITE_SETTINGS比较特殊，需要个别处理，同时这两个权限回调在onActivityResult
     *  其他权限可以以多参数的方式传入
     *  SYSTEM_ALERT_WINDOW的requestCode为SYSTEM_ALERT_WINDOW
     *  WRITE_SETTINGS的requestCode为WRITE_SETTINGS
     * @param context
     * @param permissionCallback
     *      权限申请回调
     * @param permissions
     *      多参数，多条权限申请
     */
    protected void getMorePermission(Context context, DAPermissionCallback permissionCallback, String... permissions){
        this.daPermissionCallback=permissionCallback;
        String[] a=new String[permissions.length];
        int j=0;
        for (int i=0;i<permissions.length;i++) {
            if(permissions[i].equals(Manifest.permission.SYSTEM_ALERT_WINDOW) ||
                    permissions[i].equals(Manifest.permission.WRITE_SETTINGS)){
                if(Build.VERSION.SDK_INT >= 23) {
                    if(!Settings.System.canWrite(context)){
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                Uri.parse("package:" + context.getPackageName()));
                        startActivityForResult(intent,WRITE_SETTINGS);
                        return;
                    }
                    if (!Settings.canDrawOverlays(context)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + context.getPackageName()));
                        startActivityForResult(intent, SYSTEM_ALERT_WINDOW);
                        return;
                    }

                }
            }else {
                if (ContextCompat.checkSelfPermission(context,
                        permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    a[j] = permissions[i];
                    j++;
                }
            }
        }
        if(j!=0){
            ActivityCompat.requestPermissions(getActivity(),
                    a, 100);
        }else{
            if(this.daPermissionCallback!=null) {
                this.daPermissionCallback.PermissionSuccess();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (requestCode == 100)
        {
            boolean isOk=true;
            for (int i:grantResults) {
                if(i != PackageManager.PERMISSION_GRANTED) {
                    isOk=false;
                    break;
                }
            }
            if (isOk)
            {
                if(daPermissionCallback!=null) {
                    daPermissionCallback.PermissionSuccess();
                }
            } else
            {
                if(daPermissionCallback!=null) {
                    daPermissionCallback.Fail();
                }
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    /**
     *   默认写好的dialog,当用户点击禁止时，可以直接调用这个对话框
     */
    protected void DAFailDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle("权限申请失败")
                .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
    /**
     *    默认dialog,可以自定义提示消息和点击事件,当用户点击禁止时，可以使用这个对话框
     * @param title
     *     标题
     * @param message
     *     提示信息
     * @param btn_title1
     *      按钮1的信息
     * @param btn_title2
     *      按钮2的信息
     * @param daPermissionButtonCallback
     *      按钮点击事件回调
     */
    protected void DAFailDialog(String title, String message, String btn_title1, String btn_title2,
                                final DAPermissionButtonCallback daPermissionButtonCallback){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        daPermissionButtonCallback.Btn1(dialog,which);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        daPermissionButtonCallback.Btn2(dialog,which);
                    }
                });
        builder.show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        daHandler=null;
    }
    //Handler
    public void handlerMessage(int what, Object obj){}
}
