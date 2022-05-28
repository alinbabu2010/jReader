package com.compose.jreader.data.firebase

import com.compose.jreader.data.model.BookUi
import com.compose.jreader.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
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
                    if(it.isSuccessful) trySend(null)
                    else trySend(it.exception?.localizedMessage)
                }
            }
        }
        awaitClose { cancel() }
    }


}