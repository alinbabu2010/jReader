package com.compose.jreader.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.compose.jreader.ui.model.UiState
import com.compose.jreader.data.model.Resource
import com.compose.jreader.data.model.Resource.Status

/**
 * To check [MutableState] string value is valid one
 * @return True if valid else false
 */
fun MutableState<String>.isValidInput(): Boolean =
    this.trimValue().isNotBlank()

/**
 * To trim [MutableState] string value
 * @return Trimmed string
 */
fun MutableState<String>.trimValue(): String = this.value.trim()

/**
 * To check the email is valid or not
 * @return true if it is valid else false
 */
fun String.isValidEmail(): Boolean =
    this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

/**
 * To get [UiState] model from [Resource] based on [Resource.status]
 * @return [UiState] object
 */
fun <T> Resource<T>.getUiState(): UiState<T> {
    return when (status) {
        Status.SUCCESS -> UiState(data)
        Status.ERROR -> UiState(isError = true, message = message)
        Status.LOADING -> UiState(isLoading = true)
        Status.EMPTY_RESPONSE -> UiState()
    }
}

/**
 * To show [Toast] message
 * @param message Message to be displayed
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}