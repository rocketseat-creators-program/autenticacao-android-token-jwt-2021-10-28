package com.expertsclub.expertsauthentication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.*
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class LauncherActivity : AppCompatActivity() {

    private val viewModel: LauncherViewModel by viewModels {
        LauncherViewModel.LauncherViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (viewModel.isUserLoggedIn != null) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        startActivity(
                            Intent(
                                this@LauncherActivity,
                                MainActivity::class.java
                            ).apply {
                                putExtra(
                                    MainActivity.EXTRA_USER_LOGGED_IN,
                                    viewModel.isUserLoggedIn
                                )
                            }
                        )
                        finish()
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }
}