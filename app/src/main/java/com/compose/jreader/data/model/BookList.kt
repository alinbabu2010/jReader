package com.compose.jreader.data.model

import com.google.gson.annotations.SerializedName

data class BookList(
    @SerializedName("items") val booksList: List<Book>,
    val kind: String,
    val totalItems: Int
)