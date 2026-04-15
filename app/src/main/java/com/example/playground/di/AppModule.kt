package com.example.playground.di

import android.content.Context
import com.example.playground.feature.application.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: MainApplication) {

    @Provides
    @Singleton
    fun provideApplication(): MainApplication = application

    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext
}
