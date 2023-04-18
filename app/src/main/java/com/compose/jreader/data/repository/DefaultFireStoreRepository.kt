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
class DefaultFireStoreRepository @Inject constructor(
    private val databaseSource: DatabaseSource,
    firebaseAuth: FirebaseAuth
) : FireStoreRepository {

    private val userId = firebaseAuth.currentUser?.uid
    private lateinit var data: List<BookUi>

    /**
     * To get all books from [DatabaseSource]
     * @return [Resource] for [Pair] containing two list of books response
     */
    override suspend fun getAllBooks(): Resource<Pair<List<BookUi>, List<BookUi>>> {
        return when (val response = databaseSource.getAllBooksFromFirebase()) {
            is ResponseWrapper.Error -> Resource.error(response.exception)
            is ResponseWrapper.Success -> {
                if (response.data.isNullOrEmpty()) Resource.empty()
                else {
                    data = response.data.filter {
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
    override suspend fun getBookById(bookId: String): BookUi? {
        return databaseSource.getBookById(bookId)
    }

    /**
     * To update a book info in firebase
     * @param bookId Id of the book to be updated
     * @param updateData Map containing data to updated
     */
    override suspend fun updateBook(
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
    override fun deleteBook(bookId: String) = callbackFlow {
        databaseSource.deleteBookById(bookId).collectLatest {
            trySend(it)
        }
    }

    /**
     * Get list of books read by user
     * @return List of book read by user
     */
    @Singleton
    override fun getReadBook(): List<BookUi> = data.filter {
        it.finishedReading != null
    }

    /**
     * To get number of books read by user
     * @return Number of book
     */
    override fun getReadBookCount(): Int {
        return getReadBook().size
    }

    /**
     * To get number of books currently reading by a user
     * @return Number of book
     */
    override fun getReadingBooksCount(): Int {
        return data.getReadingBooks().size
    }

}
