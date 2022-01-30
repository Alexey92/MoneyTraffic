package com.example.money.db

import android.provider.BaseColumns

object DbNameClass: BaseColumns {
    const val TABLE_NAME = "records_table"
    const val COLUMN_NAME_SUM = "sum"
    const val COLUMN_NAME_DESCRIPTION = "description"
    const val COLUMN_NAME_CATEGORY = "category"
    const val COLUMN_NAME_DATE = "date"
    const val COLUMN_NAME_TIME = "time"
    const val COLUMN_NAME_TAGS = "tags"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "Records.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
//    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_SUM INTEGER," +
            "$COLUMN_NAME_DESCRIPTION TEXT," +
            "$COLUMN_NAME_CATEGORY INTEGER," +
            "$COLUMN_NAME_DATE TEXT," +
            "$COLUMN_NAME_TIME TEXT," +
            "$COLUMN_NAME_TAGS TEXT)"

    const val SQL_DELETE_TBL = "DROP TABLE IF EXISTS $TABLE_NAME"
}