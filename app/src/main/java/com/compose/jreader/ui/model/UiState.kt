package com.compose.jreader.ui.model

data class UiState<out T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String? = null
) {

    var isEmpty = data == null && !isLoading && !isError

}
