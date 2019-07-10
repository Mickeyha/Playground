package com.example.playground.ui.main

import com.example.playground.ui.main.application.MainApplicationComponent
import dagger.Component

@Component (modules = [MainActivityModule::class], dependencies = [MainApplicationComponent::class])
interface MainActivityComponent {
    fun inject(view: MainActivity)
}