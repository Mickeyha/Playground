package com.example.playground.feature.chat

import dagger.Component

@Component(modules = [ChatActivityModule::class])
interface ChatActivityComponent {
    fun inject(view: ChatActivity)
}