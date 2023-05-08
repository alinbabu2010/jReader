package com.compose.jreader.utils

import android.content.Context
import android.icu.text.DateFormat
import android.widget.Toast
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.Resource
import com.compose.jreader.data.model.Resource.Status
import com.compose.jreader.ui.model.UiState
import com.google.firebase.Timestamp

/**
 * To check string value is valid one
 * @return True if valid else false
 */
fun String.isValidInput(): Boolean =
    this.trim().isNotBlank()

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

/**
 * To get list of books started reading
 * @return List of books
 */
fun List<BookUi>.getReadingBooks(): List<BookUi> {
    return filter {
        it.startedReading != null && it.finishedReading == null
    }
}

/**
 * To get list of books that are not started and finished reading
 * @return List of books
 */
fun List<BookUi>.getSavedBooks(): List<BookUi> {
    return filter {
        it.startedReading == null && it.finishedReading == null
    }
}