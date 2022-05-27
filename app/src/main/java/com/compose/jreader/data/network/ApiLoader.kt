package com.compose.jreader.data.network

import com.compose.jreader.data.model.Book
import com.compose.jreader.data.wrappers.ResponseWrapper

interface ApiLoader {
    fun getAllBooks(searchQuery: String): ResponseWrapper<List<Book>>
    fun getBookInfo(bookId: String): ResponseWrapper<Book>
}