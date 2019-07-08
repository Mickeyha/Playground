package com.example.playground.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playground.R
import com.example.playground.main.application.MainApplication
import com.example.playground.main.state.State
import com.example.playground.signin.SignInActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @field:[Inject]
    internal lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v("onCreate")
        setContentView(R.layout.activity_main)
        initInjections()
        presenter.create()
    }

    private fun initInjections() {
        DaggerMainActivityComponent.builder()
            .mainApplicationComponent((application as MainApplication).appComponent)
            .mainActivityModule(MainActivityModule(this))
            .build()
            .inject(this)
    }

    fun render(state: State) {
        when (state) {
            is State.startLoading -> {

            }
            is State.finishLoading -> {

            }
            is State.launchSignInPage -> {
                startActivity(Intent(this@MainActivity, SignInActivity::class.java))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.v("onDestroy")
        presenter.destroy()
    }
}
