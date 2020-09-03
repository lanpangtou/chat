package com.example.zy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public  class DatabaseHelper extends SQLiteOpenHelper {


    private final String TAG = "DatabaseHelper";

    /**
     *
     * @param context 上下文
     * @param name  数据库名称
     * @param factory   游标工厂    null默认值
     * @param version   版本
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库时的回调
        Log.e(TAG, "创建数据库.....");
        String sql = "create table "+Constants.TABLE_NAME+"(" +
                "id integer not null primary key autoincrement," +
                "name varchar(20) not null," +
                "password varchar(20) not null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级数据库时的回调
        Log.e(TAG, "升级数据库.....");
        String sql = "alter table " + Constants.TABLE_NAME + " add headImg varchar";
        db.execSQL(sql);
    }
}
