package com.example.playground.ui.main.state

sealed class State {
    object StartLoading: State()
    object FinishLoading: State()
    object LaunchSignInPage: State()
}