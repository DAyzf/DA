package com.dabaselibrary.dabaselibrary.DAUtils;

import android.os.Handler;

public class ThreadUtils {
    private static ThreadUtils threadUtils;
    private ThreadUtils(){}
    public static ThreadUtils getThreadUtils(){
        if(threadUtils==null){
            threadUtils=new ThreadUtils();
        }
        return threadUtils;
    }

    /**
     * 子线程执行task
     */
    public void runInThread(Runnable task) {
        new Thread(task).start();
    }

    /**
     * 创建一个主线程中handler
     */
    public Handler mHandler = new Handler();

    /**
     * UI线程执行task
     */
    public void runInUIThread(Runnable task) {
        mHandler.post(task);
    }
}
