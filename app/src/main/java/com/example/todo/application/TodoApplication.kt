package com.example.todo.application

import android.app.Application
import android.content.res.Configuration
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    companion object {
        lateinit var myAppInstance: TodoApplication

    }

    init {
        myAppInstance = this
    }




    }