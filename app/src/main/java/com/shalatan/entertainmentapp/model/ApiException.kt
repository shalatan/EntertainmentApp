package com.shalatan.entertainmentapp.model

class ApiException(
    message: String,
    val code: Int
) : Exception(message)