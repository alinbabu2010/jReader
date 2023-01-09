package com.compose.jreader.data.repository

import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

interface FireStoreRepository {
    /**
     * To get all books from DatabaseSource
     * @return [Resource] for [Pair] containing two list of books response
     */
    suspend fun getAllBooks(): Resource<Pair<List<BookUi>, List<BookUi>>>

    /**
     * To get book info by id
     * @param bookId Id of the book
     * @return [BookUi] object for specified bookId
     */
    suspend fun getBookById(bookId: String): BookUi?

    /**
     * To update a book info in firebase
     * @param bookId Id of the book to be updated
     * @param updateData Map containing data to updated
     */
    suspend fun updateBook(
        bookId: String,
        updateData: Map<String, Comparable<*>?>
    ): Flow<Boolean>

    /**
     * To delete a book info
     * @param bookId Id of the book to be updated
     * @return CallBackFlow sending a boolean value
     */
    fun deleteBook(bookId: String): Flow<Boolean>

    /**
     * Get list of books read by user
     * @return List of book read by user
     */
    @Singleton
    fun getReadBook(): List<BookUi>

    /**
     * To get number of books read by user
     * @return Number of book
     */
    fun getReadBookCount(): Int

    /**
     * To get number of books currently reading by a user
     * @return Number of book
     */
    fun getReadingBooksCount(): Int
}