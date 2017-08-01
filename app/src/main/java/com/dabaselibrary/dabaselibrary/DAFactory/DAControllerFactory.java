package com.dabaselibrary.dabaselibrary.DAFactory;

import android.content.Context;

import com.dabaselibrary.dabaselibrary.DAController.DABaseController;
import com.dabaselibrary.dabaselibrary.DAController.DAOpenController;
import com.dabaselibrary.dabaselibrary.DAControllerInterface.DABaseControllerInterface;
import com.dabaselibrary.dabaselibrary.DAControllerInterface.DAOpenControllerInterface;

import java.util.ArrayList;

/**
 * Created by DA on 2017/03/13.
 */

public class DAControllerFactory {
    private static DAOpenControllerInterface openController;
    private static DABaseControllerInterface baseController;

    public static DAOpenControllerInterface getOpenController(Context context){
        if(openController==null){
            openController=new DAOpenController(context);
        }
        return openController;
    }
    public static DABaseControllerInterface getBaseController(Context context){
        if(baseController==null){
            baseController = new DABaseController(context);
        }
        return baseController;
    }
}
