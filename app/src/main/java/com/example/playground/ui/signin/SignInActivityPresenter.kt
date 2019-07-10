package com.example.playground.ui.signin

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.playground.R
import com.example.playground.ui.signin.state.State
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

class SignInActivityPresenter @Inject constructor(private val view: SignInActivity,
                                                  private val context: Context
) {
    private val compositeDisposable = CompositeDisposable()

    // firebase auth
    var firebaseAuth: FirebaseAuth
    private lateinit var googleApiClient: GoogleApiClient

    init {
        compositeDisposable += view.clickSignInWithGoogleIntent.subscribe { signIn() }

        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun signIn() {
        Timber.d("signIn()")
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        view.render(State.LaunchSingInWithGoogle(signInIntent))
    }

    fun create() {
        // Configure Google Sign In
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(context)
            .enableAutoManage(view, view)
            .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOption)
            .build()
    }

    fun destroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun checkSignInResult(intent: Intent?) {
        val signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(intent)
        if (signInResult.isSuccess) {
            val googleSignInAccount = signInResult.signInAccount
            firebaseAuthWithGoogle(googleSignInAccount)
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        Timber.d("firebaseAuthWithGoogle, account = $account")

        view.render(State.StartLoading)
        val authCredential: AuthCredential = GoogleAuthProvider.getCredential(account?.idToken, null)
        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener (view) {
                Timber.d("signInWithCredential:onComplete: ${it.isSuccessful}")

                view.render(State.FinishLoading)
                if (!it.isSuccessful) {
                    Timber.w("signInWithCredential $it.exception")
                    Toast.makeText(context, "signInWithCredential", Toast.LENGTH_SHORT)
                } else {
                    view.render(State.LaunchMainActivity)
                }
            }
    }
}