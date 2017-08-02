package com.dabaselibrary.dabaselibrary.DAOpenUtils;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

/** 
 * Created by DA on 2017/02/23.
 */
public class DAWifiHotUtils {
    private WifiManager mWifiManager = null;
    private Context mContext = null;
    DAWifiHotUtils(Context context) {
        mContext = context;    
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);  
    }
    public void startWifiAp(String ssid, String passwd) {
        //wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi  
        if (mWifiManager.isWifiEnabled()) {    
            mWifiManager.setWifiEnabled(false);    
        }
        if (!isWifiApEnabled()) {  
            stratWifiAp(ssid, passwd);   
        }   
    }
    /** 
     * 设置热点名称及密码，并创建热点 
     * @param mSSID 
     * @param mPasswd 
     */  
    private void stratWifiAp(String mSSID, String mPasswd) {
        Method method1 = null;    
        try {    
            //通过反射机制打开热点  
            method1 = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);    
            WifiConfiguration netConfig = new WifiConfiguration();
            netConfig.SSID = mSSID;    
            netConfig.preSharedKey = mPasswd;
            netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);    
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);    
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);    
            netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);    
            netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);    
            netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);    
            netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);    
            netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);    
            method1.invoke(mWifiManager, netConfig, true);
        } catch (Exception e) {
            e.printStackTrace();    
        }
    }
    /** 
     * 热点开关是否打开 
     * @return 
     */
    public boolean isWifiApEnabled() {
        try {    
            Method method = mWifiManager.getClass().getMethod("isWifiApEnabled");    
            method.setAccessible(true);    
            return (Boolean) method.invoke(mWifiManager);    
        } catch (Exception e) {
            e.printStackTrace();    
        }    
        return false;    
    }
    /** 
     * 关闭WiFi热点 
     */
    public void closeWifiAp() {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);     
        if (isWifiApEnabled()) {    
            try {    
                Method method = wifiManager.getClass().getMethod("getWifiApConfiguration");    
                method.setAccessible(true);    
                WifiConfiguration config = (WifiConfiguration) method.invoke(wifiManager);    
                Method method2 = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);    
                method2.invoke(wifiManager, config, false);    
            } catch (Exception e) {
                e.printStackTrace();    
            }
        }   
    }
} 