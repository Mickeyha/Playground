package com.example.playground.signin.state

import android.content.Intent

sealed class State {
    object StartLoading: State()
    object FinishLoading: State()
    object LaunchMainActivity: State()
    data class LaunchSingInWithGoogle(val intent: Intent): State()
}