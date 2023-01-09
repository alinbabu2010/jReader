package com.compose.jreader.data.repository

import com.compose.jreader.data.model.Book
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.Resource
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    /**
     * To get list of books info from API
     * @param searchQuery Search keyword to be searched
     * @return An instance [Resource]
     */
    fun getBooks(searchQuery: String): Resource<List<Book>>

    /**
     * To get a book info from API
     * @param bookId Id of the book to be fetched
     * @return An instance [Resource]
     */
    fun getBookInfo(bookId: String): Resource<Book>

    /**
     * To save [BookUi] into database
     * @param bookData [BookUi] model of the book to be saved
     * @return Flow emitting a [String] value - On success emits a empty string else emits the error message
     */
    fun saveBookToFirebase(bookData: BookUi?): Flow<String?>
}