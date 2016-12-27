package com.ox.mylibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2016/12/26.
 */

public abstract class ManageSharedPre {


    private final String fileName;
    private Context context;

    public ManageSharedPre(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public boolean getBooble(String key, boolean defValue) {
        return getSp().getBoolean(key, defValue);
    }

    public void setBooble(String key, boolean isTrue) {
        getSp()
                .edit()
                .putBoolean(key, isTrue)
                .apply();
    }

    private SharedPreferences getSp() {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

}
