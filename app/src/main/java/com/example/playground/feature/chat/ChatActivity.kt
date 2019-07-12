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
import com.example.playground.R
import com.example.playground.feature.chat.state.State
import com.example.playground.feature.signin.SignInActivity
import com.example.playground.utils.ViewHelper
import com.example.playground.view.CommonConfirmDialog
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_chat.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChatActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    @field:[Inject]
    lateinit var presenter: ChatActivityPresenter

    lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v("onCreate")
        setContentView(R.layout.activity_chat)
        initViews()
        initInjections()
        presenter.create()
        addListeners()
    }

    @SuppressLint("CheckResult")
    private fun addListeners() {
        // Send messages on click.
        sendButton.clicks()
            .throttleLast(500, TimeUnit.MILLISECONDS)
            .filter { messageEditTextView.text.isNotEmpty() }
            .map { messageEditTextView.text.toString() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenter.sendMessage(messageEditTextView.text.toString())

            }
    }

    fun render(state: State) {
        Timber.d("render $state")
        when (state) {
            is State.StartLoading -> {
                progressBar.visibility = View.VISIBLE
            }
            is State.FinishLoading -> {
                progressBar.visibility = View.GONE
            }
            is State.LaunchSignInPage -> {
                startActivity(Intent(this@ChatActivity, SignInActivity::class.java))
                finish()
            }
            is State.ShowConfirmSignOutDialog -> {
                val commonConfirmDialog = CommonConfirmDialog(
                    context = this@ChatActivity,
                    logoId = R.drawable.ic_twotone_error_outline_24px,
                    title = "Sign out",
                    content = "Do you really wanna sign out?",
                    cancelTitle = "No",
                    confirmTitle = "yes",
                    confirmListener = View.OnClickListener {
                        presenter.signOut()
                    }
                )
                commonConfirmDialog.show()
            }
            is State.ShowRecyclerView -> {
                messageRecyclerView.adapter = state.firebaseRecyclerAdapter
            }
            is State.ClearMessageEditTextView -> {
                messageEditTextView.setText("")
            }
            is State.ScrollToPosition -> {
                messageRecyclerView.scrollToPosition(state.position)
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
        ViewHelper.INSTANCE. enableHomeAsUp(supportActionBar, true)
        ViewHelper.INSTANCE.changeHomeAsUpColor(supportActionBar, this@ChatActivity, R.color.grey)
        ViewHelper.INSTANCE.changeActionBarColor(supportActionBar, this@ChatActivity ,R.color.colorChatActionBar)

        linearLayoutManager = LinearLayoutManager(this)
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
                render(State.ShowConfirmSignOutDialog)
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
        presenter.pause()
        super.onPause()

    }

    override fun onPostResume() {
        presenter.resume()
        super.onPostResume()

    }
}
