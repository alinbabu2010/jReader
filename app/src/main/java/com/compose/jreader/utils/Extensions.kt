package com.compose.jreader.utils

import android.content.Context
import android.icu.text.DateFormat
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.compose.jreader.data.model.Resource
import com.compose.jreader.data.model.Resource.Status
import com.compose.jreader.ui.model.UiState
import com.google.firebase.Timestamp

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

/**
 * To show [Toast] message
 * @param stringId Id of the string resource
 */
fun Context.showToast(stringId: Int) {
    val message = this.getString(stringId)
    this.showToast(message)
}

/**
 * To format [Timestamp] to display in readable format
 * @return Formatted date as String
 */
fun Timestamp.formatDate(): String = DateFormat.getDateInstance()
    .format(this.toDate()).toString()
    .split(",").first()