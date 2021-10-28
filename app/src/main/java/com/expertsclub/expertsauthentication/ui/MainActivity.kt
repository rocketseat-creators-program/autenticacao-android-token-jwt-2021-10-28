package com.expertsclub.expertsauthentication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.expertsclub.expertsauthentication.R
import com.expertsclub.expertsauthentication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment

        navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.main_nav)
        val startDestinationId = if (isUserLoggedIn()) {
            R.id.homeFragment
        } else R.id.loginFragment
        navGraph.setStartDestination(startDestinationId)
        navController.graph = navGraph

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment
        ))

        binding.toolbarApp.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopLevelDestination = appBarConfiguration.topLevelDestinations.contains(destination.id)
            if (isTopLevelDestination) {
                binding.toolbarApp.navigationIcon = null
            }

            when (destination.id) {
                R.id.loginFragment -> binding.toolbarApp.isVisible = false
                else -> binding.toolbarApp.isVisible = true
            }
        }
    }

    private fun isUserLoggedIn(): Boolean {
        return intent.getBooleanExtra(EXTRA_USER_LOGGED_IN, false)
    }

    companion object {
        const val EXTRA_USER_LOGGED_IN = "USER_LOGGED_IN"
    }
}