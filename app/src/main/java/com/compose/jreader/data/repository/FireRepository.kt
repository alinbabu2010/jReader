package com.compose.jreader.data.repository

import com.compose.jreader.data.firebase.DatabaseSource
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.Resource
import com.compose.jreader.data.wrappers.ResponseWrapper
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireRepository @Inject constructor(
    private val databaseSource: DatabaseSource,
    firebaseAuth: FirebaseAuth
) {

    private val userId = firebaseAuth.currentUser?.uid

    private var bookList = mutableListOf<BookUi>()

    /**
     * To get all books from [DatabaseSource]
     * @return [Resource] for list of books response
     */
    suspend fun getAllBooks(): Resource<List<BookUi>> {
        return when (val response = databaseSource.getAllBooksFromFirebase()) {
            is ResponseWrapper.Error -> Resource.error(response.exception)
            is ResponseWrapper.Success -> {
                val data = response.data?.filterNotNull()?.filter {
                    it.userId == userId
                }
                if (data.isNullOrEmpty()) Resource.empty()
                else {
                    bookList = data.toMutableList()
                    Resource.success(bookList)
                }
            }
        }
    }

    /**
     * To get book info by id
     * @param bookId Id of the book
     * @return [BookUi] object for specified bookId
     */
    fun getBookById(bookId: String): BookUi {
        return bookList.first {
            it.googleBookId == bookId
        }
    }

}
