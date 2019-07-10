package com.example.playground.utils

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

class FirebaseHelper {

    private val firebaseAuth =  FirebaseAuth.getInstance()
    private val firebaseUser = firebaseAuth.currentUser
}