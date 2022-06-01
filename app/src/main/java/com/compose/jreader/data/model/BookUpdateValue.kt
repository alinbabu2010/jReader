package com.compose.jreader.data.model

data class BookUpdateValue(
    val notes: String,
    val isFinishedReading: Boolean,
    val isStartedReading: Boolean,
    val rating: Int
)
