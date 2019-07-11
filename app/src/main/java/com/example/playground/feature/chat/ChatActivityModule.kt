package com.example.playground.feature.chat

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ChatActivityModule(private val view: ChatActivity) {

    @Provides
    fun provideView() = view

    @Provides
    fun provideContext(): Context = view
}