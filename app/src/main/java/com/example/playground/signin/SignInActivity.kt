package com.example.playground.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playground.R
import timber.log.Timber

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        setContentView(R.layout.activity_sign_in)
    }
}
