package com.ox.chengystudio.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ListView;

import com.ox.chengystudio.R;
import com.ox.chengystudio.adapter.DrawHelperCityAdapter;
import com.ox.chengystudio.adapter.DrawHelperHuaAdapter;
import com.ox.chengystudio.base.BaseActivity;
import com.ox.chengystudio.callback.OnCityClickListener;
import com.ox.chengystudio.callback.OnHuaClickListener;
import com.ox.chengystudio.callback.OnHuaSaveListener;
import com.ox.chengystudio.decor.DecorUpdateHua;
import com.ox.chengystudio.ioc.OO;
import com.ox.chengystudio.ioc.OO_resColor;
import com.ox.greendao.City;
import com.ox.greendao.CityDao;
import com.ox.greendao.DaoMaster;
import com.ox.greendao.DaoSession;
import com.ox.greendao.Hua;
import com.ox.greendao.HuaDao;
import com.ox.greendao.TimeArea;
import com.ox.greendao.TimeAreaDao;
import com.ox.mylibrary.activityLauncher.Launcher;
import com.ox.mylibrary.util.EnumSpName;
import com.ox.mylibrary.util.ManageSharedPre;
import com.ox.mylibrary.util.UtilCollection;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class AcDrawHelper extends BaseActivity {

    private static final String DB_NAME = "db-wenshi";
    private static final String KEY_FIRST_IN = "FIRST_IN_".concat(AcDrawHelper.class.getSimpleName());

    @InjectView(R.id.lv_city)
    ListView lvCity;
    @InjectView(R.id.lv_hua)
    ListView lvHua;
    private DrawHelperCityAdapter cityAdapter;
    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private CityDao cityDao;
    private HuaDao huaDao;
    private TimeAreaDao timeAreaDao;
    private DrawHelperHuaAdapter huaAdapter;
    private City curCity;

    @OO_resColor(R.color.colorUnderLine)
    int colorUnderline;

    @Override
    protected boolean initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ac_draw_helper);
        return true;
    }

    @Override
    protected void afterViewInject(Bundle savedInstanceState) {
        OO.inject(mActivity, this);
        initDAO();

        setDivider(lvCity);
        cityAdapter = new DrawHelperCityAdapter(this);
        cityAdapter.setOnCityClickListener(new MyOnCityClickListener());
        lvCity.setAdapter(cityAdapter);

        setDivider(lvHua);
        huaAdapter = new DrawHelperHuaAdapter(this, timeAreaDao);
        huaAdapter.setOnHuaClickListener(new MyOnHuaClickListener());
        lvHua.setAdapter(huaAdapter);


    }

    private void setDivider(ListView lv) {
        lv.setDivider(new ColorDrawable(colorUnderline));
        int dh = getResources().getDimensionPixelSize(R.dimen.height_underline);
        lv.setDividerHeight(dh);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        huaAdapter.recycle();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        refreshCity();
        refreshHua();
        shell();
    }

    private void refreshHua() {
        List<Hua> huaList = loadHuaFromDB(curCity == null ? 0L : curCity.getId());
        huaList.add(0, new Hua());
        huaAdapter.refresh(huaList);
    }

    private void refreshCity() {
        List<City> listCity = loadAllCityFromDB();
        listCity.add(0, new City());
        cityAdapter.refresh(listCity);
    }

    private void shell() {
        ManageSharedPre manageSP = getManageSP(EnumSpName.SP_RUNTIME);
        if (manageSP.getBoolean(KEY_FIRST_IN, true)) {
            shell_addCityToDB();
            manageSP.setBoolean(KEY_FIRST_IN, false);
        }
    }

    private void shell_addCityToDB() {
        ArrayList<String> cityNames = new ArrayList<>();
        cityNames.add("杭州（低级）");
        cityNames.add("江南");
        cityNames.add("东越");
        cityNames.add("杭州（高级）");
        cityNames.add("九华");
        cityNames.add("徐海");
        cityNames.add("开封");
        cityNames.add("秦川");
        cityNames.add("燕云");
        cityNames.add("巴蜀");
        for (int i = 0; i < cityNames.size(); i++) {
            City city = new City();
            city.setName(cityNames.get(i));
            cityDao.insert(city);
        }
    }

    private List<TimeArea> loadTimeArea(Long huaId) {
        return timeAreaDao.queryBuilder()
                .where(TimeAreaDao.Properties.HuaId.eq(huaId))
                .list();
    }

    private List<Hua> loadHuaFromDB(Long cityId) {
        return huaDao.queryBuilder()
                .where(HuaDao.Properties.CityId.eq(cityId))
                .list();
    }

    private List<City> loadAllCityFromDB() {
        return cityDao.loadAll();
    }

    private void initDAO() {
        helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        cityDao = daoSession.getCityDao();
        huaDao = daoSession.getHuaDao();
        timeAreaDao = daoSession.getTimeAreaDao();
    }


    @Override
    protected void initOther(Bundle savedInstanceState) {

    }

    private void deleteCity(City city) {
        cityDao.delete(city);
        List<Hua> listHua = huaDao.queryBuilder().where(HuaDao.Properties.CityId.eq(city.getId())).list();
        deleteListHua(listHua);
    }

    private void deleteListHua(List<Hua> listHua) {
        if (!UtilCollection.isEmpty(listHua)) {
            for (Hua hua : listHua) {
                deleteHua(hua);
            }
        }
    }

    private void deleteHua(Hua hua) {
        huaDao.delete(hua);
        List<TimeArea> listTimeArea = timeAreaDao.queryBuilder().where(TimeAreaDao.Properties.HuaId.eq(hua.getId())).list();
        deleteListTimeArea(listTimeArea);
    }

    private void deleteListTimeArea(List<TimeArea> listTimeArea) {
        if (!UtilCollection.isEmpty(listTimeArea)) {
            for (TimeArea timeArea : listTimeArea) {
                deleteTimeArea(timeArea);
            }
        }
    }

    private void deleteTimeArea(TimeArea timeArea) {
        timeAreaDao.delete(timeArea);
    }

    public static class PreLauncher extends Launcher {

        public PreLauncher(Activity activity) {
            super(activity, AcDrawHelper.class);
        }

    }

    private class MyOnCityClickListener implements OnCityClickListener {

        @Override
        public void onCityClick(City city) {
            curCity = city;
            refreshHua();
        }

        @Override
        public void onCityDelete(City city) {
            deleteCity(city);
            cityAdapter.resetSelectPosition();
            refreshCity();
        }

        @Override
        public void onCityAdd() {
            shell_addCityToDB();
            refreshCity();
        }
    }

    private class MyOnHuaClickListener implements OnHuaClickListener {
        @Override
        public void onHuaAdd() {
            DecorUpdateHua.addHua(mActivity, new MyOnHuaSaveListener());
        }

        @Override
        public void onHuaUpdate(Hua hua, List<TimeArea> listTimeArea) {
            DecorUpdateHua.updateHua(mActivity, new MyOnHuaSaveListener(), hua, listTimeArea);
        }

        @Override
        public void onHuaDelete(Hua hua) {
            deleteHua(hua);
            refreshHua();
        }

        @Override
        public void onHuaHasDrawn(Hua hua) {
            huaDao.update(hua);
            refreshHua();
        }
    }

    private class MyOnHuaSaveListener implements OnHuaSaveListener {
        @Override
        public void add(Hua hua, List<TimeArea> listTimeArea) {
            hua.setCityId(curCity.getId());
            long huaId = huaDao.insert(hua);
            for (TimeArea timeArea : listTimeArea) {
                timeArea.setHuaId(huaId);
                timeAreaDao.insert(timeArea);
            }
            refreshHua();
        }

        @Override
        public void update(Hua hua, List<TimeArea> listTimeArea) {
            huaDao.update(hua);
            timeAreaDao.updateInTx(listTimeArea);
            refreshHua();
        }
    }


}
