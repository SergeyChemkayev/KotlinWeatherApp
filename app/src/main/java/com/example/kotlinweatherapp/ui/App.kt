package com.example.kotlinweatherapp.ui

import android.app.Application
import com.example.kotlinweatherapp.extensions.DelegateExt

class App : Application() {

    companion object {
        var instance: App by DelegateExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}