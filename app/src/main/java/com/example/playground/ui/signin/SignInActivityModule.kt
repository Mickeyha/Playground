package com.example.playground.ui.signin

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SignInActivityModule(private val activity: SignInActivity) {

    @Provides
    @Singleton
    fun provideView() = activity

    @Provides
    @Singleton
    fun provideContext(): Context = activity
}