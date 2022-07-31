package com.wing.tree.bruni.kordle.domain.usecase.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class NoParameterCoroutineUseCase<R: Any>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                Result.Success(execute())
            }
        } catch (throwable: Throwable) {
            Result.Failure(throwable)
        }
    }

    protected abstract suspend fun execute(): R
}