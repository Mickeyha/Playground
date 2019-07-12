package com.example.playground.feature.main

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(private val view: MainActivity,
                                                private val context: Context
) {

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