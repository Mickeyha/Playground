package com.example.playground.feature.application

import android.app.Application
import com.example.playground.di.AppComponent
import com.example.playground.di.AppModule
import com.example.playground.di.DaggerAppComponent
import timber.log.Timber

class MainApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
