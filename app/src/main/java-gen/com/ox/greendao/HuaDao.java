package com.ox.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HUA".
*/
public class HuaDao extends AbstractDao<Hua, Long> {

    public static final String TABLENAME = "HUA";

    /**
     * Properties of entity Hua.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property CityId = new Property(2, Long.class, "cityId", false, "CITY_ID");
        public final static Property HasDrawn = new Property(3, boolean.class, "hasDrawn", false, "HAS_DRAWN");
    }


    public HuaDao(DaoConfig config) {
        super(config);
    }
    
    public HuaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HUA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT NOT NULL ," + // 1: name
                "\"CITY_ID\" INTEGER," + // 2: cityId
                "\"HAS_DRAWN\" INTEGER NOT NULL );"); // 3: hasDrawn
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HUA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Hua entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
 
        Long cityId = entity.getCityId();
        if (cityId != null) {
            stmt.bindLong(3, cityId);
        }
        stmt.bindLong(4, entity.getHasDrawn() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Hua entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
 
        Long cityId = entity.getCityId();
        if (cityId != null) {
            stmt.bindLong(3, cityId);
        }
        stmt.bindLong(4, entity.getHasDrawn() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Hua readEntity(Cursor cursor, int offset) {
        Hua entity = new Hua( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // cityId
            cursor.getShort(offset + 3) != 0 // hasDrawn
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Hua entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setCityId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setHasDrawn(cursor.getShort(offset + 3) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Hua entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Hua entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Hua entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
