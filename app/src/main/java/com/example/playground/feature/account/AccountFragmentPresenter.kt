package com.example.playground.feature.account

import android.content.Context
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