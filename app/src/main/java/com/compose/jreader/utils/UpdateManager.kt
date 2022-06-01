package com.compose.jreader.utils

import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.BookUpdateValue
import com.google.firebase.Timestamp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateManager @Inject constructor() {

    lateinit var bookData: Map<String, Comparable<*>?>

    fun isUpdateValid(
        bookUpdateValue: BookUpdateValue,
        data: BookUi
    ): Boolean {

        val isNoteChanged = bookUpdateValue.notes != data.notes
        val isRatingChanged = bookUpdateValue.rating != data.rating

        val isValidUpdate = isNoteChanged || isRatingChanged
                || bookUpdateValue.isStartedReading || bookUpdateValue.isFinishedReading

        if (isValidUpdate) {
            generateBookData(bookUpdateValue, data)
        }

        return isValidUpdate

    }

    private fun generateBookData(bookUpdateValue: BookUpdateValue, data: BookUi) {
        val finishedReading = if (bookUpdateValue.isFinishedReading)
            Timestamp.now() else data.finishedReading
        val startedReading = if (bookUpdateValue.isStartedReading)
            Timestamp.now() else data.startedReading
        bookData = mapOf(
            "started_reading_at" to startedReading,
            "finished_reading_at" to finishedReading,
            "rating" to bookUpdateValue.rating,
            "notes" to bookUpdateValue.notes
        )
    }

}