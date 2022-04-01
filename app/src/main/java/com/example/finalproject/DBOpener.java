package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "SAVED_IMAGES";
    protected final static int VERSION_NUM = 2;
    public final static String TABLE_NAME = "IMAGES";
    public final static String COL_IMAGE_TITLE = "TITLE";
    public final static String COL_IMAGE_URL = "URL";
    public final static String COL_IMAGE_HDURL = "HDURL";
    public final static String COL_IMAGE_DESC = "DESCRIPTION";
    public final static String COL_IMAGE_DATE = "DATE";
    public final static String COL_ID = "ID";

    public DBOpener(Context ctx) { super(ctx, DATABASE_NAME, null, VERSION_NUM);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_IMAGE_TITLE + " text,"
                + COL_IMAGE_DATE + " text, "
                + COL_IMAGE_DESC + " text,"
                + COL_IMAGE_HDURL + " text,"
                + COL_IMAGE_URL + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop old table
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        //create new table
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
