package com.example.playground.ui.main.application

import dagger.Component

@Component (modules = [MainApplicationModule::class])
interface MainApplicationComponent {
    fun inject(application: MainApplication)
}