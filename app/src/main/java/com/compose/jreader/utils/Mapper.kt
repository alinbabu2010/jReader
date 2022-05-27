package com.compose.jreader.utils

import com.compose.jreader.data.model.Book
import com.compose.jreader.data.model.BookUi

interface Mapper {
    fun getBookUiList(data: List<Book>): List<BookUi>
    fun getBookUi(book: Book): BookUi
}