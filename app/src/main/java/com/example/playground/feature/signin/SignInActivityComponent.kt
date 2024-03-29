package com.example.playground.feature.signin

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SignInActivityModule::class])
interface SignInActivityComponent {
    fun inject(view: SignInActivity)
}