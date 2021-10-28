package com.expertsclub.expertsauthentication.ui

import android.content.Context
import androidx.lifecycle.*
import com.expertsclub.expertsauthentication.ExpertsApp
import com.expertsclub.expertsauthentication.base.AppCoroutinesDispatchers
import com.expertsclub.expertsauthentication.base.ResultStatus
import com.expertsclub.expertsauthentication.framework.preferences.manager.LocalPersistenceManagerImpl
import com.expertsclub.expertsauthentication.framework.network.manager.TokenManagerImpl
import com.expertsclub.expertsauthentication.data.repository.AuthRepository
import com.expertsclub.expertsauthentication.framework.preferences.datasource.PreferencesDataSourceImpl
import com.expertsclub.expertsauthentication.framework.network.datasource.RetrofitDataSourceImpl
import com.expertsclub.expertsauthentication.data.repository.UserRepository
import com.expertsclub.expertsauthentication.domain.model.User
import com.expertsclub.expertsauthentication.domain.usecase.GetUserUseCase
import com.expertsclub.expertsauthentication.domain.usecase.LogoutUseCase
import com.expertsclub.expertsauthentication.framework.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUserCase: LogoutUseCase
) : ViewModel() {

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: LiveData<AuthenticationState> = _authenticationState

    fun getUser() = getUserUseCase.invoke(Unit).watchGetUserStatus()

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

    fun logout() = logoutUserCase.invoke(Unit).watchLogoutStatus()

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
                val authDataSource = PreferencesDataSourceImpl(expertsApp.authDataStore)
                val localDataSource = PreferencesDataSourceImpl(expertsApp.localDataStore)
                val tokenManager = TokenManagerImpl(authDataSource)
                val localPersistenceManager = LocalPersistenceManagerImpl(localDataSource)
                val remoteDataSource =
                    RetrofitDataSourceImpl(ApiService.getService(tokenManager))
                val dispatchers = AppCoroutinesDispatchers(
                    io = Dispatchers.IO,
                    computation = Dispatchers.Default,
                    main = Dispatchers.Main
                )
                val authRepository = AuthRepository(remoteDataSource, tokenManager)
                val userRepository = UserRepository(remoteDataSource, localPersistenceManager)

                val getUserUserCase = GetUserUseCase(userRepository, dispatchers)
                val logoutUseCase = LogoutUseCase(authRepository, userRepository, dispatchers)

                return UserViewModel(getUserUserCase, logoutUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}