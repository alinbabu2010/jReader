package com.compose.jreader.data.model

data class ErrorBody(
    val error: Error
)

data class Error(
    val code: Int,
    val message: String
)