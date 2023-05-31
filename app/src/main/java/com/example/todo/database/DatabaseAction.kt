package com.example.todo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todo.model.Task

@Dao
interface DatabaseAction {
    @get:Query("SELECT * FROM Task")
    val allTasksList: List<Task?>?

    @Query("DELETE FROM Task")
    fun truncateTheList()

    @Insert
    fun insertDataIntoTaskList(task: Task?)

    @Query("DELETE FROM Task WHERE taskId = :taskId")
    fun deleteTaskFromId(taskId: Int)

    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    fun selectDataFromAnId(taskId: Int): Task?

    @Query(
        "UPDATE Task SET taskActive = :taskActive WHERE taskId = :taskId"
    )
    fun updateAnExistingRow(
        taskId: Int,
        taskActive : String
    )
}