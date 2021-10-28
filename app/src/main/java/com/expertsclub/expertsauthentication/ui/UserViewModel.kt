package com.expertsclub.expertsauthentication.ui

import android.content.Context
import androidx.lifecycle.*
import com.expertsclub.expertsauthentication.ExpertsApp
import com.expertsclub.expertsauthentication.base.ResultStatus
import com.expertsclub.expertsauthentication.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel : ViewModel() {

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: LiveData<AuthenticationState> = _authenticationState

    fun getUser() {
        // TODO: Implementar
    }

    private fun Flow<ResultStatus<User>>.watchGetUserStatus() = viewModelScope.launch {
        collect { status ->
            _authenticationState.value = when (status) {
                ResultStatus.Loading -> AuthenticationState.Loading
                is ResultStatus.Success -> AuthenticationState.Authenticated(status.data)
                is ResultStatus.Error -> {
                    when (val error = status.throwable) {
                        is HttpException -> {
                            if (error.code() == 401) {
                                AuthenticationState.Unauthenticated
                            } else AuthenticationState.Error
                        }
                        else -> AuthenticationState.Error
                    }
                }
            }
        }
    }

    fun logout() {
        // TODO: Implementar
    }

    private fun Flow<ResultStatus<Unit>>.watchLogoutStatus() = viewModelScope.launch {
        collect { status ->
            when (status) {
                is ResultStatus.Success ->
                    _authenticationState.value = AuthenticationState.Unauthenticated
                else -> {
                }
            }
        }
    }

    sealed class AuthenticationState {
        object Loading : AuthenticationState()
        data class Authenticated(val user: User) : AuthenticationState()
        object Unauthenticated : AuthenticationState()
        object Error : AuthenticationState()
    }

    class UserViewModelFactory(applicationContext: Context) :
        ViewModelProvider.Factory {

        private val expertsApp = applicationContext as ExpertsApp

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                // TODO: Instanciar as dependências
                return UserViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}