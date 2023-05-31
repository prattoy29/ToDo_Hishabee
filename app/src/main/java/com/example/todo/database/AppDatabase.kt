package com.example.todo.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataBaseAction(): DatabaseAction?


    companion object {
        private val appDatabase: AppDatabase? = null
    }
}