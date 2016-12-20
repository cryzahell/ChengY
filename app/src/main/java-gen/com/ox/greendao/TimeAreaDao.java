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
 * DAO for table "TIME_AREA".
*/
public class TimeAreaDao extends AbstractDao<TimeArea, Void> {

    public static final String TABLENAME = "TIME_AREA";

    /**
     * Properties of entity TimeArea.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property StartTime = new Property(0, long.class, "startTime", false, "START_TIME");
        public final static Property EndTime = new Property(1, long.class, "endTime", false, "END_TIME");
    }


    public TimeAreaDao(DaoConfig config) {
        super(config);
    }
    
    public TimeAreaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TIME_AREA\" (" + //
                "\"START_TIME\" INTEGER NOT NULL ," + // 0: startTime
                "\"END_TIME\" INTEGER NOT NULL );"); // 1: endTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TIME_AREA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TimeArea entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getStartTime());
        stmt.bindLong(2, entity.getEndTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TimeArea entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getStartTime());
        stmt.bindLong(2, entity.getEndTime());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public TimeArea readEntity(Cursor cursor, int offset) {
        TimeArea entity = new TimeArea( //
            cursor.getLong(offset + 0), // startTime
            cursor.getLong(offset + 1) // endTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TimeArea entity, int offset) {
        entity.setStartTime(cursor.getLong(offset + 0));
        entity.setEndTime(cursor.getLong(offset + 1));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(TimeArea entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(TimeArea entity) {
        return null;
    }

    @Override
    public boolean hasKey(TimeArea entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
