package com.example.mcb.genesisapp.Repository.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class to manage database creation for BasicToken repository and its version management.
 * Implements a Singleton pattern -> only one DB connection per application
 * Created by mcb on 11.02.18.
 */
public class BasicOpenHelper extends SQLiteOpenHelper {

    private static BasicOpenHelper instance;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EAV16.db";

    private BasicOpenHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        BasicSQLContracts.createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //Todo
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //super.onDowngrade(db, oldVersion, newVersion);
        //Todo
        onUpgrade(db,oldVersion,newVersion);
    }

    public static synchronized BasicOpenHelper getInstance(Context context){

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (instance == null) {
            instance = new BasicOpenHelper(context.getApplicationContext());
        }
        return instance;

    }

    public String getDatabaseName(){
        return DATABASE_NAME;
    }
}
