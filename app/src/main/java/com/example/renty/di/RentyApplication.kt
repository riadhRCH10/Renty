package com.example.renty.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RentyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}