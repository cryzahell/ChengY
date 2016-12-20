package com.ox.mylibrary.util;

import android.util.Log;

import java.text.MessageFormat;

/**
 * Created by admin on 2016/12/21.
 */

public class ManageLog {

    private static boolean debug = true;

    private static final String TAG = ManageLog.class.getSimpleName();

    /**
     * 临时调试使用，调试完成后删除代码，不受控制
     */
    public static void sout(String... msg) {
        if (msg != null && msg.length > 0) {
            String temp = MessageFormat.format("www.{0}.com", msg[0]);
            if (msg.length > 1) {
                for (int i = 1; i < msg.length; i++) {
                    temp += "." + msg[i];
                }
            }
            System.out.println(temp);
        }
    }

    public static void e(Exception e) {
        if (debug) {
            e.printStackTrace();
        }
    }

    public static void e(String msg) {
        if (debug) {
            Log.e(TAG, msg);
        }
    }

}
