package com.example.playground.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.playground.R
import com.example.playground.main.application.MainApplication
import com.example.playground.main.state.State
import com.example.playground.signin.SignInActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

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
            is State.StartLoading -> {

            }
            is State.FinishLoading -> {

            }
            is State.LaunchSignInPage -> {
                startActivity(Intent(this@MainActivity, SignInActivity::class.java))
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.v("onDestroy")
        presenter.destroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.menu_sign_out -> {
                presenter.signOut()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        Timber.e("onConnectionFailed, $result")
        Toast.makeText(this@MainActivity, "onConnectionFailed!", Toast.LENGTH_SHORT )
    }
}
