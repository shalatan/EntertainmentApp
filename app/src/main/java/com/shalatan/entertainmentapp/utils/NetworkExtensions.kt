package com.shalatan.entertainmentapp.utils

import com.app.base.StateFullResult
import com.shalatan.entertainmentapp.model.ApiException
import retrofit2.Response

fun <T : Any> Response<T>.toStateFullResult(): StateFullResult<T> {
    return try {
        if (isSuccessful && body() != null) {
            StateFullResult.success(body()!!)
        } else {
//            val errorBody = errorBody()?.charStream()?.let { reader ->
//                Gson().fromJson(reader, ApiErrorResponse::class.java)
//            }
            StateFullResult.failed(ApiException("API call failed: ${"errorBody?.message"} (${code()})", code()))
        }
    } catch (e: Exception) {
        StateFullResult.failed(e)
    }
}

data class ApiErrorResponse(val message: String?, val status: String?, val error: String?, val code: Int, val timestamp: Long)