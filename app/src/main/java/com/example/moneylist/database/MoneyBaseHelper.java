package com.example.moneylist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoneyBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "moneyBase.db";

    //初始化
    public MoneyBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

        //负责创建初始数据库
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table "+ MoneyDbSchema.MonetTable.NAME+"("
                    +"_id integer primary key autoincrement, "
                    + MoneyDbSchema.MonetTable.Cols.UUID + ", "
                    + MoneyDbSchema.MonetTable.Cols.TITLE + ", "
                    + MoneyDbSchema.MonetTable.Cols.Money + ", "
                    + MoneyDbSchema.MonetTable.Cols.Date + ", "
                    + MoneyDbSchema.MonetTable.Cols.Method + ", "
                    + MoneyDbSchema.MonetTable.Cols.Notes + ","
                    + MoneyDbSchema.MonetTable.Cols.Contanter
                    +")");

            //用户表
            sqLiteDatabase.execSQL("create table "+ MoneyDbSchema.MonetTable.NAME2+"("
                                +"_id integer primary key autoincrement, "
                                + MoneyDbSchema.MonetTable.Cols.UserId + ", "
                                + MoneyDbSchema.MonetTable.Cols.username + ", "
                                + MoneyDbSchema.MonetTable.Cols.password
                                +")");

        }

        //负责数据库表的升级工作
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }