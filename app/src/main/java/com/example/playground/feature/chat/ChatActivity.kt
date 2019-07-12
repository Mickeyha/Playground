package com.example.playground.feature.chat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playground.R
import com.example.playground.feature.chat.state.State
import com.example.playground.feature.signin.SignInActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_chat.*
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
                progressBar.visibility = View.VISIBLE
            }
            is State.FinishLoading -> {
                progressBar.visibility = View.GONE
            }
            is State.LaunchSignInPage -> {
                startActivity(Intent(this@ChatActivity, SignInActivity::class.java))
            }
            is State.ShowConfirmSignOutDialog -> {

            }
            is State.ShowRecyclerView -> {
                messageRecyclerView.adapter = state.firebaseRecyclerAdapter
            }
        }
    }

    private fun initInjections() {
        DaggerChatActivityComponent.builder()
            .chatActivityModule(ChatActivityModule(this))
            .build()
            .inject(this)
    }

    private fun initViews() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        messageRecyclerView.layoutManager = linearLayoutManager
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
            R.id.menu_sign_out -> {
                Timber.d("Sign out selected")
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

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onPostResume() {
        super.onPostResume()
        presenter.resume()
    }
}
