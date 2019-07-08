package com.example.playground.main.application

import android.app.Application
import timber.log.Timber

class MainApplication: Application() {

    lateinit var appComponent: MainApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDebug()
        initInjections()
    }

    private fun initInjections() {
        appComponent = DaggerMainApplicationComponent.builder()
            .mainApplicationModule(MainApplicationModule(this))
            .build()
            .also { it.inject(this@MainApplication) }

    }

    private fun initDebug() {
        Timber.plant(Timber.DebugTree())
    }
}