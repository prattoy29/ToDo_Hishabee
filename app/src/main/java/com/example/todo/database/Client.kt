package com.example.todo.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room.databaseBuilder

class Client private constructor(private val mCtx: Context) {
    //our app database object
    val appDatabase: AppDatabase = databaseBuilder(mCtx, AppDatabase::class.java, "Task.db")
        .fallbackToDestructiveMigration()
        .build()

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mInstance: Client? = null
        @Synchronized
        fun getInstance(mCtx: Context): Client? {
            if (mInstance == null) {
                mInstance = Client(mCtx)
            }
            return mInstance
        }
    }
}