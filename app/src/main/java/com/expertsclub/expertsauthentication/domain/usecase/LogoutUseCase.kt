package com.expertsclub.expertsauthentication.domain.usecase

import com.expertsclub.expertsauthentication.base.AppCoroutinesDispatchers
import com.expertsclub.expertsauthentication.data.ResultStatus
import com.expertsclub.expertsauthentication.data.repository.AuthRepository
import com.expertsclub.expertsauthentication.data.repository.UserRepository
import com.expertsclub.expertsauthentication.domain.ResultUseCase
import kotlinx.coroutines.withContext

class LogoutUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val dispatchers: AppCoroutinesDispatchers
) : ResultUseCase<Unit, Unit>() {

    override suspend fun doWork(params: Unit): ResultStatus<Unit> {
        return withContext(dispatchers.io) {
            authRepository.clearAccessToken()
            userRepository.clearUser()
            ResultStatus.Success(Unit)
        }
    }
}