package com.ox.mylibrary.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by admin on 2016/12/20.
 */

public class UtilMessage {

    public static void showToast(Context context, String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

}
