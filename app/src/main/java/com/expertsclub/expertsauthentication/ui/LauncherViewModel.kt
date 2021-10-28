package com.expertsclub.expertsauthentication.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.expertsclub.expertsauthentication.ExpertsApp
import com.expertsclub.expertsauthentication.base.ResultStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LauncherViewModel : ViewModel() {

    var isUserLoggedIn: Boolean? = null

    init {
        // TODO: Implementar
    }

    private fun Flow<ResultStatus<Boolean>>.watchStatus() = viewModelScope.launch {
        collect { status ->
            isUserLoggedIn = when (status) {
                is ResultStatus.Success -> status.data
                else -> null
            }
        }
    }

    class LauncherViewModelFactory(applicationContext: Context) :
        ViewModelProvider.Factory {

        private val expertsApp = applicationContext as ExpertsApp

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LauncherViewModel::class.java)) {
                // TODO: Injetar as dependências
                return LauncherViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}