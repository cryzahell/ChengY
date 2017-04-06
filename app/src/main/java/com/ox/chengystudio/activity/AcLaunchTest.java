package com.ox.chengystudio.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ox.chengystudio.R;
import com.ox.chengystudio.base.BaseActivity;
import com.ox.mylibrary.activityLauncher.Launcher;
import com.ox.mylibrary.activityLauncher.ParamForce;

import java.io.Serializable;

import butterknife.InjectView;

public class AcLaunchTest extends BaseActivity {

    @InjectView(R.id.tv_description)
    TextView tvDescription;

    @Override
    protected boolean initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ac_launch_test);
        return true;
    }

    @Override
    protected void afterViewInject(Bundle savedInstanceState) {
        tvDescription.setText(
                "需求分析：\r\n"
                .concat("\t\t\t\t接手一个老的项目时，activity 传参没有一个统一的标准，他/她会蹲出怎样的一个坑呢，画风太美难以想象~\r\n")
                .concat("\t\t\t\t所以，如果能够有一个明确的规则去约束，会减少大量的调试时间。")
        );
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
