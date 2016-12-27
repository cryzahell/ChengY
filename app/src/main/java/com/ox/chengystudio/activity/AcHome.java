package com.ox.chengystudio.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.ox.chengystudio.R;
import com.ox.chengystudio.base.BaseActivity;
import com.ox.chengystudio.entity.TestEntity;
import com.ox.mylibrary.util.UtilMessage;

import butterknife.InjectView;
import butterknife.OnClick;

public class AcHome extends BaseActivity {

    @InjectView(R.id.cb_launch_param_full)
    CheckBox cbParamFull;

    @Override
    protected boolean initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ac_home);
        return true;
    }

    @Override
    protected void afterViewInject(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initOther(Bundle savedInstanceState) {

    }

    @OnClick({R.id.btn_activity_launcher})
    void clickAcLauncher(View view) {
        AcLaunchTest.PreLauncher preLauncher = new AcLaunchTest.PreLauncher(this);
        preLauncher.test1(1).test2("2");
        if (cbParamFull.isChecked()) {
            preLauncher.test3(new TestEntity());
        }
        preLauncher.start();
//        UtilMessage.showToast(this, "clickAcLauncher");
    }

    @OnClick(R.id.btn_double_progress_bar)
    void clickDbProgressBar(View view) {
        UtilMessage.showToast(this, "clickDbProgressBar");
    }

    @OnClick(R.id.btn_draw_helper)
    void clickDrawHelper(View view) {
        new AcDrawHelper.PreLauncher(this).start();
//        UtilMessage.showToast(this, "clickDrawHelper");
    }

}
