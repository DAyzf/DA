package com.dabaselibrary.dabaselibrary.DAInterface;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by DA on 2017/07/17.
 * 总的Adapter接口
 */

public interface DAAdapterInterface {
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
    int getItemCount();
}
