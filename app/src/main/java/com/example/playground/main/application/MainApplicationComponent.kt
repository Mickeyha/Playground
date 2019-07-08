package com.example.playground.main.application

import dagger.Component

@Component (modules = [MainApplicationModule::class])
interface MainApplicationComponent {
    fun inject(application: MainApplication)
}