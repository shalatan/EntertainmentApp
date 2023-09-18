package com.shalatan.entertainmentapp.network

sealed class Response<out T : Any> {
    data class Success<out T : Any>(val data: T?) : Response<T>()
    data class Error(val exception: String, val errorCode: Int) : Response<Nothing>()
    data object Loading : Response<Nothing>()
}