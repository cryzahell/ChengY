package com.ox.chengystudio.activity;

import android.app.Activity;
import android.os.Bundle;

import com.ox.chengystudio.R;
import com.ox.chengystudio.base.BaseActivity;
import com.ox.mylibrary.activityLauncher.Launcher;
import com.ox.mylibrary.activityLauncher.ParamForce;

import java.io.Serializable;

public class AcLaunchTest extends BaseActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ac_launch_test);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initOther(Bundle savedInstanceState) {

    }

    public static class PreLauncher extends Launcher {

        @ParamForce
        private static final String KEY_PARAM_TEST1 = "PARAM_TEST1";
        @ParamForce
        private static final String KEY_PARAM_TEST2 = "PARAM_TEST2";
        @ParamForce
        private static final String KEY_PARAM_TEST3 = "PARAM_TEST_3";
        private static final String KEY_PARAM_TEST4 = "PARAM_TEST4";

        public PreLauncher(Activity activity) {
            super(activity, AcLaunchTest.class);
        }

        public PreLauncher test1(int test1) {
            intent.putExtra(KEY_PARAM_TEST1, test1);
            return this;
        }

        public PreLauncher test2(String test2) {
            intent.putExtra(KEY_PARAM_TEST2, test2);
            return this;
        }

        public PreLauncher test3(Serializable test3) {
            intent.putExtra(KEY_PARAM_TEST3, test3);
            return this;
        }

        public PreLauncher test4(long test4) {
            intent.putExtra(KEY_PARAM_TEST4, test4);
            return this;
        }

    }

}
