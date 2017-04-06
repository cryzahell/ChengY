package com.ox.mylibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2016/12/26.
 */

public class ManageSharedPre {

    private final SharedPreferences sp;

    public ManageSharedPre(Context context, EnumSpName fileName) {
        sp = getSp(context, fileName.name());
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public void setBoolean(String key, boolean isTrue) {
        sp.edit().putBoolean(key, isTrue).apply();
    }

    private SharedPreferences getSp(Context context, String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

}
