package com.example.playground.main

import android.content.Context
import com.example.playground.main.state.State
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(private val view: MainActivity,
                                                private val context: Context
) {

    companion object {
        const val ANONYMOUS = "anonymous"
    }

    private var userName: String? = null

    private val compositeDisposable = CompositeDisposable()

    // firebase
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
    private val googleApiClient = GoogleApiClient.Builder(context).enableAutoManage(view, view).addApi(Auth.GOOGLE_SIGN_IN_API).build()

    fun create() {
        Timber.d("create")
        userName = ANONYMOUS

        // Not sign in yet, launch the SignInActivity
        if (firebaseUser == null) {
            view.render(State.LaunchSignInPage)
        } else {    // get the pic of user
            userName = firebaseUser.displayName
            // TODO getUserPhoto
        }
    }

    fun destroy() {
        Timber.d("destroy")

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        Auth.GoogleSignInApi.signOut(googleApiClient)
        userName = ANONYMOUS
        view.render(State.LaunchSignInPage)
    }
}