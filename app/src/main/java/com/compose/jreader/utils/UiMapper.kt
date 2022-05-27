package com.compose.jreader.utils

import androidx.core.text.HtmlCompat
import com.compose.jreader.data.model.Book
import com.compose.jreader.data.model.BookUi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UiMapper @Inject constructor() : Mapper {

    override fun getBookUiList(data: List<Book>): List<BookUi> = data.map { getBookUi(it) }

    override fun getBookUi(book: Book): BookUi {
        val bookInfo = book.volumeInfo
        return BookUi(
            googleBookId = book.id ?: "",
            title = bookInfo?.title ?: "",
            photoUrl = bookInfo?.imageLinks?.smallThumbnail ?: "",
            authors = getAuthors(bookInfo?.authors),
            description = getDescription(bookInfo?.description),
            categories = getCategories(bookInfo?.categories),
            publishedDate = bookInfo?.publishedDate ?: "",
            pageCount = bookInfo?.pageCount ?: 0
        )
    }

    private fun getDescription(description: String?): String =
        HtmlCompat.fromHtml(description ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

    private fun getAuthors(authors: List<String>?) =
        if (authors.isNullOrEmpty()) "" else authors.toString()

    private fun getCategories(categories: List<String>?) =
        if (categories.isNullOrEmpty()) "" else categories.toString()

}