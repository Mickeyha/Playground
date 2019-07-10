package com.example.playground.ui.main.application

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainApplicationModule(private val application: MainApplication) {

    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application
}