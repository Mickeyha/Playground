package com.example.playground.feature.chat

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ChatFragmentModule(private val view: ChatFragment) {

    @Provides
    fun provideView() = view

    @Provides
    fun provideContext(): Context = view.requireContext()
}