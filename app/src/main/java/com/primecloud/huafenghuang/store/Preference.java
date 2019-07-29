package com.primecloud.huafenghuang.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Preference {
    public static void saveStringPreferences(Context context, String key, String defValue) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, defValue);
        editor.commit();
    }
    public static void saveBoolPreferences(Context context, String key, boolean defValue) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(key, defValue);
        editor.commit();
    }
    public static boolean getBoolPreferences(Context context, String key, boolean defValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defValue);
    }
}
