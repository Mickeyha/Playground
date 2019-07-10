package com.example.playground.ui.account.state

import com.google.firebase.auth.FirebaseUser

sealed class State {
    object StartLoading: State()
    object FinishLoading: State()
    object LaunchSignInPage: State()
    data class ShowUserData(val firebaseUser: FirebaseUser): State()
}