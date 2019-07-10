package com.example.playground.ui.account

import dagger.Component

@Component (modules = [AccountFragmentModule::class])
interface AccountFragmentComponent {
    fun inject(view: AccountFragment)
}