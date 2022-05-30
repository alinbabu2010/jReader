package com.compose.jreader.data.repository

import com.compose.jreader.data.firebase.DatabaseSource
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.Resource
import com.compose.jreader.data.wrappers.ResponseWrapper
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FireRepository @Inject constructor(
    private val databaseSource: DatabaseSource,
    firebaseAuth: FirebaseAuth
) {

    private val userId = firebaseAuth.currentUser?.uid

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
                else Resource.success(data)
            }
        }
    }

}
