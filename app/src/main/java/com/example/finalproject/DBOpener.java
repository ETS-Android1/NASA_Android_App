package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The DBOpener class
 *
 * @author Aladah M + Will B
 * @version 1.00
 * DBOpener class allows app to create and use a database
 * Users are shown images via the NASA api and can choose to save them
 * The SQL database holds images and associated data
 *
 */

public class DBOpener extends SQLiteOpenHelper {
    //variables to hold column names
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

    /**
     * The first time the app is opened DB table will be created
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_IMAGE_TITLE + " text,"
                + COL_IMAGE_DATE + " text, "
                + COL_IMAGE_DESC + " text,"
                + COL_IMAGE_HDURL + " text,"
                + COL_IMAGE_URL + " text);");
    }

    /**
     * If a new table is created this method will drop the old table and create the new table
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop old table
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        //create new table
        onCreate(sqLiteDatabase);
    }

    /**
     * This method will update database table if current version needs replacing by an older version
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
