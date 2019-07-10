package com.example.playground.feature.main

import com.example.playground.feature.main.application.MainApplicationComponent
import dagger.Component

@Component (modules = [MainActivityModule::class], dependencies = [MainApplicationComponent::class])
interface MainActivityComponent {
    fun inject(view: MainActivity)
}