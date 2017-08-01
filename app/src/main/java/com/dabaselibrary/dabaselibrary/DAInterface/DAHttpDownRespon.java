package com.dabaselibrary.dabaselibrary.DAInterface;

/**
 * Created by DA on 2017/03/14.
 */

public interface DAHttpDownRespon {
    /**
     *      下载进度回调
     * @param bytesRead
     *      以下载字节
     * @param contentLength
     *      总字节
     * @param done
     *      是否下载完成
     */
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
