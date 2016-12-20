package com.ox.chengystudio.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.ox.chengystudio.R;
import com.ox.greendao.DaoMaster;
import com.ox.greendao.DaoSession;
import com.ox.greendao.Hua;
import com.ox.greendao.HuaDao;
import com.ox.mylibrary.base.BaseActivity;
import com.ox.mylibrary.util.CollectionUtil;

import java.util.List;

public class AcHome extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_home);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "hua-db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        DaoSession session = master.newSession();

        HuaDao huaDao = session.getHuaDao();

        List<Hua> huaList = huaDao.loadAll();

//        if (!CollectionUtil.isEmpty(huaList)) {
//            for (Hua hua : huaList) {
////                System.out.println("www." + hua.getName() + ".com");
////                System.out.println("www." + hua.getHasDrawn() + ".com");
//                System.out.println("www." + hua.getId() + ".com");
//            }
//        }

    }
}
