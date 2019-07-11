package com.example.playground.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.playground.R
import com.example.playground.feature.main.application.MainApplication
import com.example.playground.feature.main.state.State
import com.example.playground.feature.signin.SignInActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @field:[Inject]
    internal lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v("onCreate")
        setContentView(R.layout.activity_main)
        initViews()
        initInjections()
        presenter.create()
    }

    private fun initViews() {
        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        setupBottomNavMenu(navHost.navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav_view.setupWithNavController(navController)
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
        return when (item?.itemId) {
            R.id.menu_chat -> {
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
