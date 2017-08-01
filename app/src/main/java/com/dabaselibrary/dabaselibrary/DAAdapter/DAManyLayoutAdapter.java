package com.dabaselibrary.dabaselibrary.DAAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.dabaselibrary.dabaselibrary.DAInterface.DAManyLayoutAdapterInterface;
import com.dabaselibrary.dabaselibrary.R;

/**
 * Created by DA on 2017/07/21.
 * 多页面的Adapter
 */

public class DAManyLayoutAdapter extends DAAdapter {
    private DAManyLayoutAdapterInterface platformManyLayoutAdapterInterface;
    public int loadID= R.layout.item_adapter_load;
    public DAManyLayoutAdapter(DAManyLayoutAdapterInterface p, RecyclerView rv,
                               Context context, boolean isLine) {
        super(p, rv, context, isLine);
        this.platformManyLayoutAdapterInterface=p;
    }
    @Override
    public int getItemViewType(int position) {
        return platformManyLayoutAdapterInterface.getItemViewType(position);
    }
    //上拉加载更多
    public static class LoadViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout ll_root;
        public LoadViewHolder(View itemView) {
            super(itemView);
            ll_root=(LinearLayout) itemView.findViewById(R.id.item_adapter_Root);
        }
    }
}
