package caixin.android.com.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import caixin.android.com.Application;

public class DeviceStateUtil {

    @SuppressLint("MissingPermission")
    public static String getDeviceId(){
        TelephonyManager telephonyManager = (TelephonyManager) Application.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
       return telephonyManager.getDeviceId();
    }
}
