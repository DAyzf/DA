package com.dabaselibrary.dabaselibrary.DAOpenUtils;

import android.content.Context;

public class DADensityUtil {
    private Context context;
    DADensityUtil(Context context){this.context=context;}
    public int dp2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }
    public int px2dp(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }
    public int px2sp(float pxValue) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
            return (int) (pxValue / fontScale + 0.5f);  
    }
    public int sp2px(float spValue) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
            return (int) (spValue * fontScale + 0.5f);}
}
