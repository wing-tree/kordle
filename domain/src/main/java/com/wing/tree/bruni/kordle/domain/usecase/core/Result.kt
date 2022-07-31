package com.wing.tree.bruni.kordle.domain.usecase.core

sealed class Result<out R> {
    data class Failure(val throwable: Throwable) : Result<Nothing>()
    data class Success<out T: Any>(val data: T) : Result<T>()
    object Loading : Result<Nothing>()
}

inline fun <R: Any, T: Any> Result<T>.map(transform: (T) -> R): Result<R> {
    return when(this) {
        is Result.Failure -> Result.Failure(throwable)
        is Result.Success -> Result.Success(transform(data))
        Result.Loading -> Result.Loading
    }
}

fun <T: Any> Result<T>.getOrDefault(defaultValue: T): T {
    return when(this) {
        Result.Loading -> defaultValue
        is Result.Failure -> defaultValue
        is Result.Success -> data
    }
}

fun <T: Any> Result<T>.getOrNull(): T? {
    return when(this) {
        Result.Loading -> null
        is Result.Failure -> null
        is Result.Success -> data
    }
}