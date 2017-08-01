package com.dabaselibrary.dabaselibrary.DAAdapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dabaselibrary.dabaselibrary.DAInterface.DAAdapterInterface;
import com.dabaselibrary.dabaselibrary.DAView.DADividerItemDecoration;

/**
 * Created by DA on 2017/07/17.
 * 通用Adapter
 */

public class DAAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private DAAdapterInterface p;
    public DAAdapter(DAAdapterInterface p, RecyclerView rv, Context context, boolean
            isLine){
        rv.setLayoutManager(new LinearLayoutManager(context));
        if(isLine) {
            rv.addItemDecoration(new DADividerItemDecoration(context,
                    DADividerItemDecoration.VERTICAL_LIST));
        }
        rv.setAdapter(this);
        this.p=p;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return p.onCreateViewHolder(parent, viewType);}
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        p.onBindViewHolder(holder, position);}
    @Override
    public int getItemCount() {return p.getItemCount();}
}
