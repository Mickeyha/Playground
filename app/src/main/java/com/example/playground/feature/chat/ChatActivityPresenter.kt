package com.example.playground.feature.chat

import android.content.Context
import android.net.Uri
import com.example.playground.feature.chat.State.State
import com.example.playground.feature.main.MainActivityPresenter
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class ChatActivityPresenter @Inject constructor(val view: ChatActivity,
                                                val context: Context
) {

    companion object {
        const val ANONYMOUS = "anonymous"
    }
    private var userName: String? = null
    private var userPhotoUrl: Uri? = null
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
            userPhotoUrl = firebaseUser.photoUrl
        }
    }

    fun destroy() {
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