package com.compose.jreader.utils

import com.compose.jreader.data.model.Book
import com.compose.jreader.data.model.BookUi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UiMapper @Inject constructor() {

    fun getBookUiList(data: List<Book>): List<BookUi> = data.map { getBookUi(it) }

    fun getBookUi(book: Book): BookUi {
        val bookInfo = book.volumeInfo
        return BookUi(
            id = book.id ?: "",
            bookInfo?.title ?: "",
            bookInfo?.imageLinks?.smallThumbnail ?: "",
            authors = getAuthors(bookInfo?.authors),
            categories = getCategories(bookInfo?.categories),
            publishedDate = bookInfo?.publishedDate ?: ""
        )
    }

    private fun getAuthors(authors: List<String>?) =
        if (authors.isNullOrEmpty()) "" else authors.toString()

    private fun getCategories(categories: List<String>?) =
        if (categories.isNullOrEmpty()) "" else categories.toString()

}