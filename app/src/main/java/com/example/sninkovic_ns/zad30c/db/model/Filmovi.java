package com.example.sninkovic_ns.zad30c.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by SNinkovic_ns on 14.3.2017.
 */
@DatabaseTable(tableName = Filmovi.TABLE_NAME_FILMOVI)
public class Filmovi  {


    public static final String TABLE_NAME_FILMOVI= "filmovi";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_MOVIE_NAME = "name";
    public static final String FIELD_MOVIE_GENRE = "genre";
    public static final String FIELD_NAME_YEAR = "year";
    public static final String FIELD_NAME_GLUMAC= "glumac";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mid;
    @DatabaseField(columnName =FIELD_MOVIE_NAME )
    private String mName;
    @DatabaseField(columnName =FIELD_MOVIE_GENRE )
    private String mGenre;
    @DatabaseField(columnName = FIELD_NAME_YEAR )
    private String mYear;
    @DatabaseField(columnName =FIELD_NAME_GLUMAC, foreign = true, foreignAutoRefresh = true)
    private Glumac mGlumac;

    public Filmovi() {    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmGenre() {
        return mGenre;
    }

    public void setmGenre(String mGenre) {
        this.mGenre = mGenre;
    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public Glumac getmGlumac() {
        return mGlumac;
    }

    public void setmGlumac(Glumac mGlumac) {
        this.mGlumac = mGlumac;
    }

    @Override
    public String toString() {
        return mName;
    }
}
