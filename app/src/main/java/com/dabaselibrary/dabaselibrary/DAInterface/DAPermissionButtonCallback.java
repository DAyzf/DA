package com.dabaselibrary.dabaselibrary.DAInterface;

import android.content.DialogInterface;

/**
 * Created by DA on 2017/03/13.
 */

public interface DAPermissionButtonCallback {
    /**
     *      接口框架自定义弹窗回调 button1回调
     * @param dialog
     * @param which
     */
    void Btn1(DialogInterface dialog, int which);
    /**
     *      接口框架自定义弹窗回调 button2回调
     * @param dialog
     * @param which
     */
    void Btn2(DialogInterface dialog, int which);
}
