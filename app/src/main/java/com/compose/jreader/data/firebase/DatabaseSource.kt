package com.compose.jreader.data.firebase

import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.wrappers.ResponseWrapper
import com.compose.jreader.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DatabaseSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) {

    /**
     * To save [BookUi] into [FirebaseFirestore]
     * @param bookData [BookUi] model of the book to be saved
     * @return CallBackFlow emitting a [Pair]<[Boolean],[String]>
     * to denote task success and message in case of error
     */
    fun saveBookToFirebase(bookData: BookUi?) = callbackFlow {
        bookData?.userId = firebaseAuth.currentUser?.uid ?: ""
        val dbCollection = fireStore.collection(Constants.BOOK_DB_NAME)
        if ((bookData != null) && bookData.toString().isNotBlank()) {
            dbCollection.add(bookData).addOnSuccessListener { ref ->
                dbCollection.document(ref.id).update(
                    mapOf(Constants.USER_ID to ref.id)
                ).addOnCompleteListener {
                    if (it.isSuccessful) trySend(null)
                    else trySend(it.exception?.localizedMessage)
                }
            }
        }
        awaitClose { cancel() }
    }

    /**
     * To get all books from firebase fireStore
     * @return [ResponseWrapper] with list of books or exception in case of error
     */
    suspend fun getAllBooksFromFirebase(): ResponseWrapper<List<BookUi?>> {
        val dbCollection = fireStore.collection(Constants.BOOK_DB_NAME)
        return try {
            val data = dbCollection.get().await().documents.map { snapshot ->
                snapshot.toObject(BookUi::class.java)
            }
            ResponseWrapper.Success(data)
        } catch (exception: FirebaseFirestoreException) {
            ResponseWrapper.Error(exception.code.value(), exception)
        }
    }

    /**
     * To update a book info
     * @param bookId Id of the book to be updated
     * @return CallBackFlow sending a boolean value
     */
    fun updateBookById(bookId: String, updateData: Map<String, Comparable<*>?>) = callbackFlow {
        val dbCollection = fireStore.collection(Constants.BOOK_DB_NAME)
        dbCollection.document(bookId).update(updateData).addOnCompleteListener {
            trySend(it.isSuccessful)
        }
        awaitClose { cancel() }
    }

    /**
     * To get a book info
     * @param bookId Id of the book to be updated
     * @return An object [BookUi] containing book info
     */
    suspend fun getBookById(bookId: String): BookUi? {
        val dbCollection = fireStore.collection(Constants.BOOK_DB_NAME)
        return dbCollection.document(bookId).get().await().toObject(BookUi::class.java)
    }

    /**
     * To delete a book info
     * @param bookId Id of the book to be updated
     * @return CallBackFlow sending a boolean value
     */
    fun deleteBookById(bookId: String) = callbackFlow {
        val dbCollection = fireStore.collection(Constants.BOOK_DB_NAME)
        dbCollection.document(bookId).delete().addOnCompleteListener {
            trySend(it.isSuccessful)
        }
        awaitClose { cancel() }
    }

}