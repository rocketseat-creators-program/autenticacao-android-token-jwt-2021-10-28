package com.expertsclub.expertsauthentication.domain.usecase

import com.expertsclub.expertsauthentication.base.AppCoroutinesDispatchers
import com.expertsclub.expertsauthentication.data.ResultStatus
import com.expertsclub.expertsauthentication.data.repository.UserRepository
import com.expertsclub.expertsauthentication.domain.ResultUseCase
import kotlinx.coroutines.withContext

class CheckUserLoggedInUseCase(
    private val userRepository: UserRepository,
    private val dispatchers: AppCoroutinesDispatchers
) : ResultUseCase<Unit, Boolean>() {

    override suspend fun doWork(params: Unit): ResultStatus<Boolean> {
        return withContext(dispatchers.io) {
            ResultStatus.Success(userRepository.isUserLoggedIn())
        }
    }
}