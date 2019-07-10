package com.example.playground.feature.chat

import dagger.Component

@Component(modules = [ChatFragmentModule::class])
interface ChatFragmentComponent {
    fun inject(view: ChatFragment)
}