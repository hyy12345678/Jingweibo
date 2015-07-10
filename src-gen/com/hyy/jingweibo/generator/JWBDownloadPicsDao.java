package com.hyy.jingweibo.generator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.hyy.jingweibo.generator.JWBDownloadPics;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table JWBDOWNLOAD_PICS.
*/
public class JWBDownloadPicsDao extends AbstractDao<JWBDownloadPics, Long> {

    public static final String TABLENAME = "JWBDOWNLOAD_PICS";

    /**
     * Properties of entity JWBDownloadPics.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Url = new Property(1, String.class, "url", false, "URL");
        public final static Property Path = new Property(2, String.class, "path", false, "PATH");
        public final static Property Size = new Property(3, Long.class, "size", false, "SIZE");
        public final static Property Time = new Property(4, Long.class, "time", false, "TIME");
        public final static Property Type = new Property(5, Integer.class, "type", false, "TYPE");
    };


    public JWBDownloadPicsDao(DaoConfig config) {
        super(config);
    }
    
    public JWBDownloadPicsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'JWBDOWNLOAD_PICS' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'URL' TEXT," + // 1: url
                "'PATH' TEXT," + // 2: path
                "'SIZE' INTEGER," + // 3: size
                "'TIME' INTEGER," + // 4: time
                "'TYPE' INTEGER);"); // 5: type
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'JWBDOWNLOAD_PICS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, JWBDownloadPics entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(3, path);
        }
 
        Long size = entity.getSize();
        if (size != null) {
            stmt.bindLong(4, size);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(5, time);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(6, type);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public JWBDownloadPics readEntity(Cursor cursor, int offset) {
        JWBDownloadPics entity = new JWBDownloadPics( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // url
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // path
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // size
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // time
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5) // type
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, JWBDownloadPics entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPath(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSize(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setTime(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setType(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(JWBDownloadPics entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(JWBDownloadPics entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}