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
            id = book.id,
            bookInfo.title,
            bookInfo.imageLinks.smallThumbnail,
            getAuthors(bookInfo.authors),
            publishedDate = bookInfo.publishedDate
        )
    }

    private fun getAuthors(authors: List<String>?): String {
        var writers = ""
        if (!authors.isNullOrEmpty()) {
            if (authors.size == 1) writers = authors.first()
            else {
                authors.forEachIndexed { index, author ->
                    writers = if (index != authors.size)
                        "$writers$author, " else author
                }
            }
        }
        return writers
    }

}