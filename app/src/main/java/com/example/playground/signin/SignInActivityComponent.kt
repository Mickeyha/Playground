package com.example.playground.signin

import dagger.Component

@Component(modules = [SignInActivityModule::class])
interface SignInActivityComponent {
    fun inject(view: SignInActivity)
}