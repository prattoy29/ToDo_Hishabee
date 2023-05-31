package com.example.todo.database

import android.content.Context
import com.example.todo.model.Task
import javax.inject.Inject

class Repository @Inject constructor(
) : SafeApiCall {

    suspend fun getTodoList(
        context: Context
    ) = safeApiCall {
        getSavedTasks(context = context)
    }

    suspend fun taskActive(
        context: Context,
        id: Int
    ) = safeApiCall {
        makeTaskActive(context, id)
    }

    suspend fun taskInactive(
        context: Context,
        id: Int
    ) = safeApiCall {
        makeTaskInactive(context, id)
    }

    private fun getSavedTasks(context: Context): ArrayList<Task> {
        var tasks: ArrayList<Task> = ArrayList()
        tasks = Client
            .getInstance(context)
            ?.appDatabase
            ?.dataBaseAction()
            ?.allTasksList as ArrayList<Task>
        return tasks
    }

    private fun makeTaskActive(context: Context, id: Int): String {
        Client.getInstance(context)?.appDatabase
            ?.dataBaseAction()
            ?.updateAnExistingRow(
                id, "1"
            )
        return "Task edited"
    }

    private fun makeTaskInactive(context: Context, id: Int): String {
        Client.getInstance(context)?.appDatabase
            ?.dataBaseAction()
            ?.updateAnExistingRow(
                id, "0"
            )
        return "Task edited"
    }
}