package com.example.money.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.money.Record

class RecordDbManager(context: Context) {
    val recordDbHelper = RecordDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb(){
        db = recordDbHelper.writableDatabase
    }


    fun insertToDb( data: Record){
        val values = ContentValues().apply {
            put(DbNameClass.COLUMN_NAME_SUM, data.sum)
            put(DbNameClass.COLUMN_NAME_DESCRIPTION, data.description)
            put(DbNameClass.COLUMN_NAME_CATEGORY, data.category)
            put(DbNameClass.COLUMN_NAME_DATE, data.date)
            put(DbNameClass.COLUMN_NAME_TIME, data.time)
            put(DbNameClass.COLUMN_NAME_TAGS, data.tags)
        }

        db?.insert(DbNameClass.TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun readDbData(): ArrayList<Record>{
        val dataList = ArrayList<Record>()
        val cursor = db?.query(DbNameClass.TABLE_NAME,
            null, null, null, null, null, null)

        with(cursor){
            while(this?.moveToNext()!!){
                val data = Record()
                data.sum = cursor?.getInt(cursor.getColumnIndex(DbNameClass.COLUMN_NAME_SUM))
                data.description = cursor?.getString(cursor.getColumnIndex(DbNameClass.COLUMN_NAME_DESCRIPTION))
                data.category = cursor?.getInt(cursor.getColumnIndex(DbNameClass.COLUMN_NAME_CATEGORY))
                data.date = cursor?.getString(cursor.getColumnIndex(DbNameClass.COLUMN_NAME_DATE))
                data.time = cursor?.getString(cursor.getColumnIndex(DbNameClass.COLUMN_NAME_TIME))
                data.tags = cursor?.getString(cursor.getColumnIndex(DbNameClass.COLUMN_NAME_TAGS))

                dataList.add(data)
            }
        }

        cursor?.close()

        return dataList
    }

    fun closeDb(){
        recordDbHelper.close()
    }
}

