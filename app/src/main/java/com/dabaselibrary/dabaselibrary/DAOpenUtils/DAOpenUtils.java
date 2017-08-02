package com.dabaselibrary.dabaselibrary.DAOpenUtils;

import android.content.Context;

/**
 * Created by DA on 2017/08/02.
 * 总的Utils类
 */

public class DAOpenUtils {
    private DASpTool daSpTool;
    private DADownFileUtils daDownFileUtils;
    private DADialogs daDialogs;
    private DADensityUtil daDensityUtil;
    private static DAOpenUtils daOpenUtils;
    public static DAOpenUtils getObject(Context context){
        if(daOpenUtils==null){
            daOpenUtils=new DAOpenUtils(context);
        }
        return daOpenUtils;
    }
    private DAOpenUtils(Context context){
        daSpTool=new DASpTool(context);
        daDownFileUtils=new DADownFileUtils(context);
        daDialogs=new DADialogs();
        daDensityUtil=new DADensityUtil(context);
    }

    public DASpTool getDASpTool(){return daSpTool;}
    public DADownFileUtils getDaDownFileUtils(){return daDownFileUtils;}
    public DADialogs getDaDialogs(){return daDialogs;}
    public DADensityUtil getDaDensityUtil(){return daDensityUtil;}
}
