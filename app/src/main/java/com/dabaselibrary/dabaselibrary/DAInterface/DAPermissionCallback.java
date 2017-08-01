package com.dabaselibrary.dabaselibrary.DAInterface;

/**
 * Created by DA on 2017/03/13.
 */

/**
 * 申请权限回调  PermissionSuccess->申请成功  Fail->申请失败
 */
public interface DAPermissionCallback {
    void PermissionSuccess();
    void Fail();
}
