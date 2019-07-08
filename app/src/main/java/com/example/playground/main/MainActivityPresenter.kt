package com.example.playground.main

import com.example.playground.main.state.State
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(private val view: MainActivity) {

    private val compositeDisposable = CompositeDisposable()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser: FirebaseUser? = firebaseAuth.currentUser

    fun create() {
        Timber.d("create")

        // Not sign in yet, launch the SignInActivity
        if (firebaseUser == null) {
            view.render(State.launchSignInPage)
        } else {    // get the pic of user

        }
    }

    fun destroy() {
        Timber.d("destroy")

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}