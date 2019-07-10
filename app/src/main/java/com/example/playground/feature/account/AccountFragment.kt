package com.example.playground.feature.account

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.playground.R
import com.example.playground.feature.account.state.State
import com.example.playground.feature.signin.SignInActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.fragment_account.*
import timber.log.Timber
import javax.inject.Inject

class AccountFragment : Fragment(), GoogleApiClient.OnConnectionFailedListener {

    @field:[Inject]
    internal  lateinit var presenter: AccountFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_account, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initInjections()
        presenter.create()
    }

    private fun initInjections() {
        DaggerAccountFragmentComponent.builder()
            .accountFragmentModule(AccountFragmentModule(this))
            .build()
            .inject(this)
    }

    private fun initViews() {

    }

    fun render(state: State) {
        when(state) {
            is State.StartLoading -> {}
            is State.FinishLoading -> {}
            is State.LaunchSignInPage -> {
                startActivity(Intent(requireContext(), SignInActivity::class.java))
                activity?.finish()
            }
            is State.ShowUserData -> {
                user_name.text = state.firebaseUser.displayName
                Glide.with(requireContext())
                    .load(state.firebaseUser.photoUrl)
                    .into(user_pic)
            }
        }
    }

    @SuppressLint("ShowToast")
    override fun onConnectionFailed(result: ConnectionResult) {
        Timber.e("onConnectionFailed, $result")
        Toast.makeText(requireContext(), "onConnectionFailed!", Toast.LENGTH_SHORT )
    }

}