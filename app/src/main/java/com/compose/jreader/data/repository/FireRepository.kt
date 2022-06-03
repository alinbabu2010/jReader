package com.compose.jreader.data.repository

import com.compose.jreader.data.firebase.DatabaseSource
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.Resource
import com.compose.jreader.data.wrappers.ResponseWrapper
import com.compose.jreader.utils.getReadingBooks
import com.compose.jreader.utils.getSavedBooks
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireRepository @Inject constructor(
    private val databaseSource: DatabaseSource,
    firebaseAuth: FirebaseAuth
) {

    private val userId = firebaseAuth.currentUser?.uid

    /**
     * To get all books from [DatabaseSource]
     * @return [Resource] for [Pair] containing two list of books response
     */
    suspend fun getAllBooks(): Resource<Pair<List<BookUi>, List<BookUi>>> {
        return when (val response = databaseSource.getAllBooksFromFirebase()) {
            is ResponseWrapper.Error -> Resource.error(response.exception)
            is ResponseWrapper.Success -> {
                if (response.data.isNullOrEmpty()) Resource.empty()
                else {
                    val data = response.data.filter {
                        it?.userId == userId
                    }.filterNotNull()
                    Resource.success(Pair(data.getReadingBooks(), data.getSavedBooks()))
                }
            }
        }
    }

    /**
     * To get book info by id
     * @param bookId Id of the book
     * @return [BookUi] object for specified bookId
     */
    suspend fun getBookById(bookId: String): BookUi? {
        return databaseSource.getBookById(bookId)
    }

    /**
     * To update a book info in firebase
     * @param bookId Id of the book to be updated
     * @param updateData Map containing data to updated
     */
    suspend fun updateBook(
        bookId: String,
        updateData: Map<String, Comparable<*>?>
    ) = channelFlow {
        databaseSource.updateBookById(bookId, updateData)
            .collectLatest { trySend(it) }
    }

    /**
     * To delete a book info
     * @param bookId Id of the book to be updated
     * @return CallBackFlow sending a boolean value
     */
    fun deleteBook(bookId: String) = callbackFlow {
        databaseSource.deleteBookById(bookId).collectLatest {
            trySend(it)
        }
    }


}
