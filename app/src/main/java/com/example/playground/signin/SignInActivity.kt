package com.example.playground.signin

import android.graphics.Color
import android.graphics.LightingColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playground.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import timber.log.Timber

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        setContentView(R.layout.activity_sign_in)
        initViews()
        initInjects()
    }

    private fun initViews() {
        sign_in_image.colorFilter = LightingColorFilter(Color.WHITE, Color.WHITE)
    }

    private fun initInjects() {
        DaggerSignInActivityComponent.builder()
            .signInActivityModule(SignInActivityModule(this@SignInActivity))
            .build()
            .inject(this@SignInActivity)
    }
}
