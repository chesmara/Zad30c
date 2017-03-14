package com.example.sninkovic_ns.zad30c.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sninkovic_ns.zad30c.db.model.Filmovi;
import com.example.sninkovic_ns.zad30c.db.model.Glumac;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper  extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    = "priprema.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<Glumac, Integer> mGlumacDao = null;
    private Dao<Filmovi, Integer> mFilmoviDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
          //  TableUtils.createTable(connectionSource, Movie.class);
            TableUtils.createTable(connectionSource, Glumac.class);
            TableUtils.createTable(connectionSource,Filmovi.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Glumac.class, true);
            TableUtils.dropTable(connectionSource, Filmovi.class,true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Glumac, Integer> getmGlumacDao() throws SQLException {
        if (mGlumacDao == null) {
            mGlumacDao = getDao(Glumac.class);
        }

        return mGlumacDao;
    }

    public Dao<Filmovi,Integer> getmFilmoviDao() throws SQLException {
        if (mFilmoviDao == null){
            mFilmoviDao=getDao(Filmovi.class);
        }
        return mFilmoviDao;
    }
    @Override
    public void close() {
       // mProductDao = null;
        mGlumacDao = null;
        mFilmoviDao=null;

        super.close();
    }


}
