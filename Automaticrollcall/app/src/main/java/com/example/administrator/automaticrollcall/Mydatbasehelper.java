package com.example.administrator.automaticrollcall;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/8.
 */
public class Mydatbasehelper extends SQLiteOpenHelper{
    final String CREATE_TABLE_SQL = "create table study(_id integer primary " +
            "key autoincrement,classnum,studentnum,studentname,lanya,telphone)";
    final String CREATE_TABLE_SQL1 = "create table record(_id integer primary " +
            "key autoincrement,classnum,studentnum,studentname,ischecked,recordid,telphone)";
    final String CREATE_TABLE_SQL2 = "create table attendance(_id integer primary key " +
            "autoincrement,recordid)";
    final String CREATE_TABLE_SQL3 = "create table tab(_id integer primary key " +
            "autoincrement,studentname,studentnum,timess)";
    public Mydatbasehelper(Context context,String name,int version){
        super(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
        db.execSQL(CREATE_TABLE_SQL1);
        db.execSQL(CREATE_TABLE_SQL2);
        db.execSQL(CREATE_TABLE_SQL3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("----update called---"+oldVersion+"--->"+newVersion);

    }
}
