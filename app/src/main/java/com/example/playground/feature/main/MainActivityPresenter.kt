package com.example.playground.feature.main

import android.content.Context
import com.example.playground.feature.main.state.State
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

    fun create() {
        Timber.d("create")
    }

    fun destroy() {
        Timber.d("destroy")

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}