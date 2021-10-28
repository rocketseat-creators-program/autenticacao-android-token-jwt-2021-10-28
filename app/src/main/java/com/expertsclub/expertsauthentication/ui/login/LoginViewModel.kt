package com.expertsclub.expertsauthentication.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.expertsclub.expertsauthentication.ExpertsApp

class LoginViewModel : ViewModel() {

    private val _loginStateData = MutableLiveData<LoginState>()
    val loginStateData: LiveData<LoginState> = _loginStateData

    fun login(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // TODO: Implementar
        } else _loginStateData.value = LoginState.ShowError
    }

    sealed class LoginState {
        object ShowLoading : LoginState()
        object LoginSuccess : LoginState()
        object ShowError : LoginState()
    }

    class LoginViewModelFactory(applicationContext: Context) :
        ViewModelProvider.Factory {

        private val expertsApp = applicationContext as ExpertsApp

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                // TODO: Instanciar as dependências
                return LoginViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}