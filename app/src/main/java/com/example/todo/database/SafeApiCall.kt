package com.example.todo.database


import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SafeApiCall {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                throwable.message?.let { Log.e(throwable.toString(), it) }
                when (throwable) {
                    else -> {
                        Resource.Failure(false, null, "Something went wrong... Please try again")
                    }
                }
            }
        }
    }
}