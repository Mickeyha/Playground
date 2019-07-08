package com.example.playground.main.state

sealed class State {
    object StartLoading: State()
    object FinishLoading: State()
    object LaunchSignInPage: State()
}