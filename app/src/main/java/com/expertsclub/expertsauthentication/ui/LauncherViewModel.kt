package com.expertsclub.expertsauthentication.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.expertsclub.expertsauthentication.ExpertsApp
import com.expertsclub.expertsauthentication.base.AppCoroutinesDispatchers
import com.expertsclub.expertsauthentication.data.ResultStatus
import com.expertsclub.expertsauthentication.data.manager.LocalPersistenceManagerImpl
import com.expertsclub.expertsauthentication.data.manager.TokenManagerImpl
import com.expertsclub.expertsauthentication.data.repository.PreferencesDataSourceImpl
import com.expertsclub.expertsauthentication.data.repository.RetrofitDataSourceImpl
import com.expertsclub.expertsauthentication.data.repository.UserRepository
import com.expertsclub.expertsauthentication.domain.usecase.CheckUserLoggedInUseCase
import com.expertsclub.expertsauthentication.framework.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LauncherViewModel(checkUserLoggedInUseCase: CheckUserLoggedInUseCase) : ViewModel() {

    var isUserLoggedIn: Boolean? = null

    init {
        checkUserLoggedInUseCase.invoke(Unit).watchStatus()
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
                val userRepository = UserRepository(remoteDataSource, localPersistenceManager)

                val checkUserLoggedInUseCase = CheckUserLoggedInUseCase(userRepository, dispatchers)

                return LauncherViewModel(checkUserLoggedInUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}