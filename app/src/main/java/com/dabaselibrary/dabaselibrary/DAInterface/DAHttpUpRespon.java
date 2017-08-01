package com.dabaselibrary.dabaselibrary.DAInterface;

/**
 * Created by DA on 2017/03/14.
 */

public interface DAHttpUpRespon {
    /**
     *      上传进度回调
     * @param bytesWritten
     *      已读取字节
     * @param contentLength
     *      需要上传的总字节
     * @param done
     *      是否上传完成
     */
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
