package com.example.playground.feature.signin

import android.content.Intent
import android.graphics.Color
import android.graphics.LightingColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.playground.R
import com.example.playground.feature.chat.ChatActivity
import com.example.playground.feature.main.MainActivity
import com.example.playground.feature.signin.state.State
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_sign_in.*
import timber.log.Timber
import javax.inject.Inject

class SignInActivity : AppCompatActivity(),
    GoogleApiClient.OnConnectionFailedListener {

    internal lateinit var clickSignInWithGoogleIntent: Observable<Unit>

    @field:[Inject]
    lateinit var presenter: SignInActivityPresenter

    companion object {
        const val RC_SIGN_IN = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate()")
        setContentView(R.layout.activity_sign_in)
        initViews()
        initInjects()
        presenter.create()
    }

    private fun initViews() {
        supportActionBar?.hide()
        sign_in_image.colorFilter = LightingColorFilter(Color.WHITE, Color.DKGRAY)
        clickSignInWithGoogleIntent = sign_in_google_button.clicks()
    }

    private fun initInjects() {
        DaggerSignInActivityComponent.builder()
            .signInActivityModule(SignInActivityModule(this@SignInActivity))
            .build()
            .inject(this@SignInActivity)
    }

    fun render(state: State) {
        when (state) {
            is State.StartLoading -> {
                progressBar.visibility = View.VISIBLE
            }
            is State.FinishLoading -> {
                progressBar.visibility = View.GONE
            }
            is State.LaunchSingInWithGoogle -> {
                startActivityForResult(state.intent, RC_SIGN_IN)
            }
            is State.LaunchMainActivity -> {
                startActivity(Intent(this@SignInActivity, ChatActivity::class.java))
                finish()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy()")
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        Timber.e("onConnectionFailed, $result")
        Toast.makeText(this@SignInActivity, "onConnectionFailed!", Toast.LENGTH_SHORT )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == RC_SIGN_IN) {
            presenter.checkSignInResult(intent)
        }
    }
}
