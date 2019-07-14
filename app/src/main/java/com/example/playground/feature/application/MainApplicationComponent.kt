package com.example.playground.feature.application

import dagger.Component

@Component (modules = [MainApplicationModule::class])
interface MainApplicationComponent {
    fun inject(application: MainApplication)
}