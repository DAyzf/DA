package com.dabaselibrary.dabaselibrary.DAOpenUtils;

import android.content.Context;

/**
 * @author DA
 * Sp管理
 */
public class DASpTool {
	private Context context;
	DASpTool(Context context){
		this.context=context;
	}
	public void setInt(String name,int value){
		context.getSharedPreferences(context.getPackageName(),0)
				.edit().putInt(context.getPackageName()+name, value).apply();
	}
	public int getInt(String name,int value){
		return context.getSharedPreferences(context.getPackageName(),0)
				.getInt(context.getPackageName()+name, value);
	}
	public void setString(String name,String value){
		context.getSharedPreferences(context.getPackageName(),0).edit()
				.putString(context.getPackageName()+name, value).apply();
	}
	public String getString(String name,String value){
		return context.getSharedPreferences(context.getPackageName(),0)
				.getString(context.getPackageName()+name, value);
	}
	public void setBoolean (String name,Boolean value){
		context.getSharedPreferences(context.getPackageName(), 0).edit()
				.putBoolean(context.getPackageName()+name, value).apply();
	}
	public boolean getBoolean(String name,Boolean value){
		return context.getSharedPreferences(context.getPackageName(),0).
				getBoolean(context.getPackageName()+name, value);
	}
}
