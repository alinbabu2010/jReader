package com.compose.jreader.data.repository

import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.Resource
import com.compose.jreader.data.wrappers.ResponseWrapper
import com.compose.jreader.network.ApiLoader
import com.compose.jreader.utils.Constants
import com.compose.jreader.utils.Mapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val apiLoader: ApiLoader,
    private val mapper: Mapper,
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) {

    /**
     * To get list of books info from API
     * @param searchQuery Search keyword to be searched
     * @return An instance [Resource]
     */
    fun getBooks(searchQuery: String): Resource<List<BookUi>> {
        return when (val response = apiLoader.getAllBooks(searchQuery)) {
            is ResponseWrapper.Error -> Resource.error(response.exception)
            is ResponseWrapper.Success -> {
                if (response.data.isNullOrEmpty()) Resource.empty()
                else Resource.success(mapper.getBookUiList(response.data))
            }
        }
    }

    /**
     * To get a book info from API
     * @param bookId Id of the book to be fetched
     * @return An instance [Resource]
     */
    fun getBookInfo(bookId: String): Resource<BookUi> {
        return when (val response = apiLoader.getBookInfo(bookId)) {
            is ResponseWrapper.Error -> Resource.error(response.exception)
            is ResponseWrapper.Success -> {
                if (response.data == null) Resource.empty()
                else Resource.success(mapper.getBookUi(response.data))
            }
        }
    }

    /**
     * To save [BookUi] into [FirebaseFirestore]
     * @param bookData [BookUi] model of the book to be saved
     * @param onComplete Callback function to call on successful database save.
     */
    fun saveBookToFirebase(bookData: BookUi?, onComplete: (Boolean, String?) -> Unit) {
        bookData?.userId = firebaseAuth.currentUser?.uid ?: ""
        val dbCollection = fireStore.collection(Constants.BOOK_DB_NAME)
        if ((bookData != null) && bookData.toString().isNotBlank()) {
            dbCollection.add(bookData).addOnSuccessListener { ref ->
                dbCollection.document(ref.id).update(
                    mapOf(Constants.USER_ID to ref.id)
                ).addOnCompleteListener {
                    onComplete(it.isSuccessful, it.exception?.message)
                }
            }
        }
    }

}