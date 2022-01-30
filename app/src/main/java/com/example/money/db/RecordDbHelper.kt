package com.example.money.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecordDbHelper(context: Context): SQLiteOpenHelper(context, DbNameClass.DATABASE_NAME,
    null, DbNameClass.DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(DbNameClass.CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DbNameClass.SQL_DELETE_TBL)
        onCreate(p0)
    }
}