package com.example.playground.feature.main

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private val activity: MainActivity) {

    @Provides
    fun provideView() = activity

    @Provides
    fun provideContext(): Context = activity
}