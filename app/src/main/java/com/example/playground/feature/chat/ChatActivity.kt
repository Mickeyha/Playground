package com.example.playground.feature.chat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.playground.R
import com.example.playground.feature.chat.State.State
import com.example.playground.feature.signin.SignInActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import timber.log.Timber
import javax.inject.Inject

class ChatActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    @field:[Inject]
    lateinit var presenter: ChatActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v("onCreate")
        setContentView(R.layout.activity_chat)
        initViews()
        initInjections()
        presenter.create()
    }

    fun render(state: State) {
        when (state) {
            is State.StartLoading -> {

            }
            is State.FinishLoading -> {

            }
            is State.LaunchSignInPage -> {
                startActivity(Intent(this@ChatActivity, SignInActivity::class.java))
                finish()
            }
        }
    }

    private fun initInjections() {

    }

    private fun initViews() {

    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.v("onDestroy")
        presenter.destroy()
    }

    @SuppressLint("ShowToast")
    override fun onConnectionFailed(result: ConnectionResult) {
        Timber.e("onConnectionFailed, $result")
        Toast.makeText(this@ChatActivity, "onConnectionFailed!", Toast.LENGTH_SHORT )
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_chat -> {
                presenter.signOut()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat_menu, menu)
        return true
    }
}
