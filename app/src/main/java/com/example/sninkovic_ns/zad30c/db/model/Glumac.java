package com.example.sninkovic_ns.zad30c.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by SNinkovic_ns on 12.3.2017.
 */
@DatabaseTable(tableName = Glumac.TABLE_NAME_USERS)
public class Glumac {


    public static final String TABLE_NAME_USERS = "actor";
    public static final String FIELD_NAME_ID     = "id";
    public static final String TABLE_GLUMAC_NAME = "name";
    public static final String TABLE_GLUMAC_BIOGRAPHY = "biography";
    public static final String TABLE_GLUMAC_SCORE = "score";
    public static final String TABLE_GLUMAC_BIRTH = "birth";
    public static final String TABLE_GLUMAC_FILMOVI = "filmovi";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = TABLE_GLUMAC_NAME)
    private String mName;

    @DatabaseField(columnName = TABLE_GLUMAC_BIOGRAPHY)
    private String mBiography;

    @DatabaseField(columnName = TABLE_GLUMAC_SCORE)
    private Float mScore;

    @DatabaseField(columnName = TABLE_GLUMAC_BIRTH)
    private String mBirth;

  @ForeignCollectionField(columnName = Glumac.TABLE_GLUMAC_FILMOVI, eager = true)
    private ForeignCollection<Filmovi> filmovi;

    public Glumac() {
    }
    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ForeignCollection<Filmovi> getFilmovi() {
        return filmovi;
    }

    public void setFilmovi(ForeignCollection<Filmovi> filmovi) {
        this.filmovi = filmovi;
    }

    public String getmBiography() {
        return mBiography;
    }

    public void setmBiography(String mBiography) {
        this.mBiography = mBiography;
    }

    public Float getmScore() {
        return mScore;
    }

    public void setmScore(Float mScore) {
        this.mScore = mScore;
    }

    public String getmBirth() {
        return mBirth;
    }

    public void setmBirth(String mBirth) {
        this.mBirth = mBirth;
    }

    @Override
    public String toString() {
        return mName;
    }

}
