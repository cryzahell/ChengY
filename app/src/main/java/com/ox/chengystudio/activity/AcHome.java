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
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ac_home);
//
////        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "hua-db");
////        SQLiteDatabase db = helper.getWritableDatabase();
////        DaoMaster master = new DaoMaster(db);
////        DaoSession session = master.newSession();
////
////        HuaDao huaDao = session.getHuaDao();
////
////        List<Hua> huaList = huaDao.loadAll();
//
//    }

    @InjectView(R.id.cb_launch_param_full)
    CheckBox cbParamFull;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ac_home);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initOther(Bundle savedInstanceState) {

    }

    @OnClick({R.id.btn_activity_launcher})
    void clickAcLauncher() {
        AcLaunchTest.PreLauncher preLauncher = new AcLaunchTest.PreLauncher(this);
        preLauncher.test1(1).test2("2");
        if (cbParamFull.isChecked()) {
            preLauncher.test3(new TestEntity());
        }
        preLauncher.start();
    }

    @OnClick(R.id.btn_double_progress_bar)
    void clickDbProgressBar(View view) {
        UtilMessage.showToast(this, "clickDbProgressBar");
    }

    @OnClick(R.id.btn_draw_helper)
    void clickDrawHelper(View view) {
        UtilMessage.showToast(this, "clickDrawHelper");
    }

}
