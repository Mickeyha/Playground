package com.example.playground.ui.account

import android.content.Context
import com.example.playground.ui.account.state.State
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AccountFragmentPresenter @Inject constructor(private val view: AccountFragment,
                                                   private val context: Context) {

    private var userName: String? = null


    private val compositeDisposable = CompositeDisposable()

    fun create() {

    }

    fun destroy() {
    }
}