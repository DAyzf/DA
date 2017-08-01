package com.dabaselibrary.dabaselibrary.DAInterface;

/**
 * Created by DA on 2017/07/21.
 * 多页面的Adapter接口
 */

public interface DAManyLayoutAdapterInterface extends DAAdapterInterface {
    int getItemViewType(int position);
}
