package com.ox.mylibrary.util;

import android.app.Activity;

/**
 * Created by admin on 2016/12/20.
 */

public class ManageAcAnim {

    public static void fadeInFadeOut(Activity activity) {
        if (activity != null) {
            activity.overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
            );
        }
    }

}
