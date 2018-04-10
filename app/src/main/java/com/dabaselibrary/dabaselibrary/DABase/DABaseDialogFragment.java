package com.dabaselibrary.dabaselibrary.DABase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.dabaselibrary.dabaselibrary.DAHandler.DADialogFragmentHandler;
import com.dabaselibrary.dabaselibrary.R;

/**
 * Created by DA on 2017/05/07.
 */

public abstract class DABaseDialogFragment extends DialogFragment {
    //窗口
    private WindowManager.LayoutParams attributes;
    private Window window;
    protected DADialogFragmentHandler daHandler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater,container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        window = getDialog().getWindow();
        if(window !=null){
            attributes = window.getAttributes();
            setAnimation(attributes);
        }
        daHandler=new DADialogFragmentHandler(this);
        getViewObject(view);
        return view;
    }
    //设置动画效果   --->需要自己设计动画的请重写

    /**
     * 需要动画的请重写此方法
     * @param attributes
     */
    protected void setAnimation(WindowManager.LayoutParams attributes){}
    protected abstract View initView(LayoutInflater inflater, ViewGroup container);
    protected void getViewObject(View view) {}
    @Override
    public void onStart() {
        super.onStart();
        setWindow(attributes,window);
        initData();
        initEvent();
    }
    //修改window的东西  --->需要自定义的请重写

    /**
     * 关于window的东西，如果不满意，请重写此方法
     * @param attributes
     * @param window
     */
    protected void setWindow(WindowManager.LayoutParams attributes,Window window) {
        attributes.dimAmount = 0f;
        attributes.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(attributes);
    }
    //点击事件
    protected void initEvent(){}
    //加载数据
    protected void initData(){}
    @Override
    public void onDestroy() {
        super.onDestroy();
        daHandler=null;
    }
    //Handler
    public void handlerMessage(int what, Object obj){}
}
