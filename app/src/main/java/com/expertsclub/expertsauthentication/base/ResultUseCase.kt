package com.expertsclub.expertsauthentication.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class ResultUseCase<in Params, R> {

    operator fun invoke(params: Params): Flow<ResultStatus<R>> = flow {
        emit(ResultStatus.Loading)
        emit(doWork(params))
    }.catch { throwable ->
        emit(ResultStatus.Error(throwable))
    }

    protected abstract suspend fun doWork(params: Params): ResultStatus<R>
}