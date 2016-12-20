package com.ox.mylibrary.util;

import android.app.Activity;

/**
 * Created by admin on 2016/12/20.
 */

public class ManageAcAnim {

    public static void leftInRightOut(Activity activity) {
        if (activity != null) {
            activity.overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
            );
        }
    }

}
