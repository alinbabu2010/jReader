package com.compose.jreader.data.wrappers

/**
 * Class to handle API responses
 */
sealed class ResponseWrapper<out T : Any> {
    data class Success<out T : Any>(val data: T?) : ResponseWrapper<T>()
    data class Error<out T : Any>(val errorCode: Int, val exception: Throwable) :
        ResponseWrapper<T>()
}