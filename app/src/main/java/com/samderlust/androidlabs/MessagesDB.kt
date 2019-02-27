package com.samderlust.androidlabs

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MessagesDB(context: Context): SQLiteOpenHelper (context, "MessagesDB", null, 1){

    companion object {
        val TABLE_NAME = "messages"
        val TEXT = "text"
        val SENDER = "sender"
        val ID = "id"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TEXT +  " TEXT, " + SENDER + " BOOLEAN)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME");
    }
}