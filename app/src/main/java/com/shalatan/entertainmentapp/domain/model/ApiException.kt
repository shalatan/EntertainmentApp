package com.shalatan.entertainmentapp.domain.model

class ApiException(
    message: String,
    val code: Int
) : Exception(message)