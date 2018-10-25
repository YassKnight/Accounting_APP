package com.knight.asus_nb.accounting_app.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "account.db";
    private static final int VERSION = 1;
    //建表语句
    private String sql = "create table  if not exists record("
            + "id integer primary key, "
            + "uuid text,"
            + "amount double,"
            + "type text,"
            + "day text,"
            + "remark text,"
            + "recordtype integer,"
            + "time text )";

    public MySQLiteOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库表格
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
