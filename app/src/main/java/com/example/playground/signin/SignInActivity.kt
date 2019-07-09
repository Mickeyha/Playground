package com.example.playground.signin

import android.content.Intent
import android.graphics.Color
import android.graphics.LightingColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.playground.R
import com.example.playground.main.MainActivity
import com.example.playground.signin.state.State
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
        signInImage.colorFilter = LightingColorFilter(Color.WHITE, Color.DKGRAY)
        clickSignInWithGoogleIntent = signInGoogleButton.clicks()
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
                startActivity(Intent(this@SignInActivity, MainActivity::class.java))
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
