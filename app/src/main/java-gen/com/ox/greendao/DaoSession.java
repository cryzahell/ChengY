package com.ox.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.ox.greendao.City;
import com.ox.greendao.Hua;
import com.ox.greendao.TimeArea;

import com.ox.greendao.CityDao;
import com.ox.greendao.HuaDao;
import com.ox.greendao.TimeAreaDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cityDaoConfig;
    private final DaoConfig huaDaoConfig;
    private final DaoConfig timeAreaDaoConfig;

    private final CityDao cityDao;
    private final HuaDao huaDao;
    private final TimeAreaDao timeAreaDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cityDaoConfig = daoConfigMap.get(CityDao.class).clone();
        cityDaoConfig.initIdentityScope(type);

        huaDaoConfig = daoConfigMap.get(HuaDao.class).clone();
        huaDaoConfig.initIdentityScope(type);

        timeAreaDaoConfig = daoConfigMap.get(TimeAreaDao.class).clone();
        timeAreaDaoConfig.initIdentityScope(type);

        cityDao = new CityDao(cityDaoConfig, this);
        huaDao = new HuaDao(huaDaoConfig, this);
        timeAreaDao = new TimeAreaDao(timeAreaDaoConfig, this);

        registerDao(City.class, cityDao);
        registerDao(Hua.class, huaDao);
        registerDao(TimeArea.class, timeAreaDao);
    }
    
    public void clear() {
        cityDaoConfig.clearIdentityScope();
        huaDaoConfig.clearIdentityScope();
        timeAreaDaoConfig.clearIdentityScope();
    }

    public CityDao getCityDao() {
        return cityDao;
    }

    public HuaDao getHuaDao() {
        return huaDao;
    }

    public TimeAreaDao getTimeAreaDao() {
        return timeAreaDao;
    }

}
