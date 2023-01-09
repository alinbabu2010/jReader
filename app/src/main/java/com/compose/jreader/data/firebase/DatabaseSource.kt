package com.compose.jreader.data.firebase

import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.wrappers.ResponseWrapper
import kotlinx.coroutines.flow.Flow

interface DatabaseSource {

    /**
     * To save [BookUi] into Firebase Firestore
     * @param bookData [BookUi] model of the book to be saved
     * @return CallBackFlow emitting a [Pair]<[Boolean],[String]>
     * to denote task success and message in case of error
     */
    fun saveBookToFirebase(bookData: BookUi?): Flow<String?>

    /**
     * To get all books from firebase fireStore
     * @return [ResponseWrapper] with list of books or exception in case of error
     */
    suspend fun getAllBooksFromFirebase(): ResponseWrapper<List<BookUi?>>

    /**
     * To update a book info
     * @param bookId Id of the book to be updated
     * @return CallBackFlow sending a boolean value
     */
    fun updateBookById(bookId: String, updateData: Map<String, Comparable<*>?>): Flow<Boolean>

    /**
     * To get a book info
     * @param bookId Id of the book to be updated
     * @return An object [BookUi] containing book info
     */
    suspend fun getBookById(bookId: String): BookUi?

    /**
     * To delete a book info
     * @param bookId Id of the book to be updated
     * @return CallBackFlow sending a boolean value
     */
    fun deleteBookById(bookId: String): Flow<Boolean>
}