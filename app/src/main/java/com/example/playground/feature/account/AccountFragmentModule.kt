package com.example.playground.feature.account

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AccountFragmentModule(private val view: AccountFragment) {

    @Provides
    fun provideAccountFragment() = view

    @Provides
    fun provideContext(): Context = view.requireContext()
}