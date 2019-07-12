package com.example.playground.feature.main.state

sealed class State {
    object StartLoading: State()
    object FinishLoading: State()
    object LaunchChatPage: State()
}