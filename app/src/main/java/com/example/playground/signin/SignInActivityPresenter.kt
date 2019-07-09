package com.example.playground.signin

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.playground.signin.state.State
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
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
    var googleApiClient: GoogleApiClient

    init {
        compositeDisposable += view.clickSignInWithGoogleIntent.subscribe { signIn() }

        firebaseAuth = FirebaseAuth.getInstance()
        googleApiClient = GoogleApiClient.Builder(context).enableAutoManage(view, view).addApi(Auth.GOOGLE_SIGN_IN_API).build()
    }

    private fun signIn() {
        Timber.d("signIn()")
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        view.render(State.LaunchSingInWithGoogle(signInIntent))
    }

    fun create() {

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

        val authCredential: AuthCredential = GoogleAuthProvider.getCredential(account?.idToken, null)
        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener (view) {
                Timber.d("signInWithCredential:onComplete: ${it.isSuccessful}")

                if (!it.isSuccessful) {
                    Timber.w("signInWithCredential $it.exception")
                    Toast.makeText(context, "signInWithCredential", Toast.LENGTH_SHORT)
                } else {
                    view.render(State.LaunchMainActivity)
                }
            }
    }
}