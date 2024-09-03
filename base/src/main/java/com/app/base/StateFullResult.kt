package com.app.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class StateFullResult<T> {

    class Loading<T> : StateFullResult<T>()
    data class Success<T>(val data: T) : StateFullResult<T>()
    data class Failure<T>(val exception: Exception) : StateFullResult<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(e: Exception) = Failure<T>(e)
    }
}

inline fun <T> flowResult(crossinline block: () -> StateFullResult<T>): Flow<StateFullResult<T>> {
    return flow {
        emit(StateFullResult.loading<T>())
        try {
            emit(block())
        } catch (ex: Exception) {
            emit(StateFullResult.failed(ex))
        }
    }
}

inline fun <T> autoResult(block: () -> T): StateFullResult<T> = try {
    StateFullResult.success(block())
} catch (e: Exception) {
    StateFullResult.Failure(exception = e)

}

fun <T> StateFullResult<T>.getDataOrNull(): T? {
    return (this as? StateFullResult.Success)?.data
}

suspend fun <T> StateFullResult<T>.onSuccess(callback: suspend (T) -> Unit): StateFullResult<T> {
    if (this is StateFullResult.Success) {
        callback(this.data)
    }
    return this
}

suspend fun <T> StateFullResult<T>.onFailure(
    callback: suspend (Exception) -> Unit
): StateFullResult<T> {
    if (this is StateFullResult.Failure) {
        callback(this.exception)
    }
    return this
}