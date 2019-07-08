package com.example.playground.main.state

sealed class State {
    object startLoading: State()
    object finishLoading: State()
    object launchSignInPage: State()
}