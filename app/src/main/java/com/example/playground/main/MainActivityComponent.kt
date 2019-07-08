package com.example.playground.main

import com.example.playground.main.application.MainApplicationComponent
import dagger.Component

@Component (modules = [MainActivityModule::class], dependencies = [MainApplicationComponent::class])
interface MainActivityComponent {
    fun inject(view: MainActivity)
}