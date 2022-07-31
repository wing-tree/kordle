package com.wing.tree.bruni.kordle.domain.usecase.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

abstract class NoParameterFlowUseCase <R: Any>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(): Flow<Result<R>> = execute()
        .map <R, Result<R>> { Result.Success(it) }
        .catch { emit(Result.Failure(it)) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(): Flow<R>
}