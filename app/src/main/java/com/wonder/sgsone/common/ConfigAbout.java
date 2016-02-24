package com.wonder.sgsone.common;

import android.content.SharedPreferences;

import com.wonder.sgsone.MainActivity;

/**
 * Created by u6031313 on 2/24/2016.
 */
public class ConfigAbout {
    public static final String CFG_SWITCH_IMG = "cfgSwitchImg";
    public static final String CFG_TIP_GUESS_FIG = "cfgGuessFig";
    static String sConName = "config";

    public static String Readconfig(String configName)
    {
        SharedPreferences localSharedPreferences = MainActivity.mSwitchView.mActivity.getSharedPreferences(sConName, 0);
        if (localSharedPreferences == null) {
            return "";
        }
        if (localSharedPreferences.contains(configName)) {
            return localSharedPreferences.getString(configName, "");
        }
        return null;
    }

    public static void WriteConfig(String configName, String configValue)
    {
        SharedPreferences.Editor localEditor = MainActivity.mSwitchView.mActivity.getSharedPreferences(sConName, 0).edit();
        if ((configName != null) && (configName.length() > 0) && (configValue != null))
        {
            localEditor.putString(configName, configValue);
            localEditor.commit();
        }
    }
}
