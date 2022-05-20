package com.compose.jreader.data.model

import com.compose.jreader.utils.Status

data class LoadingState(
    val status: Status,
    val message: String? = null
) {

    companion object {
        val IDLE = LoadingState(Status.IDLE)
        val SUCCESS = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.LOADING)
        val FAILED = LoadingState(Status.FAILED)
    }

}
