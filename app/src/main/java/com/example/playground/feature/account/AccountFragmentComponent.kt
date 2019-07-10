package com.example.playground.feature.account

import dagger.Component

@Component (modules = [AccountFragmentModule::class])
interface AccountFragmentComponent {
    fun inject(view: AccountFragment)
}