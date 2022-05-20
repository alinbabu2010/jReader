package com.compose.jreader.data.repository

import com.compose.jreader.data.model.MUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) {

    /**
     * To login user using [FirebaseAuth] with email and password
     * @param email Email id of the user
     * @param password Password for login
     */
    fun signIn(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful, task.exception?.message)
            }
    }

    /**
     * To create user using [FirebaseAuth] with email and password
     * @param email Email id of the new user
     * @param password Password for user creation
     */
    fun createUser(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) generateUserModel(getDisplayName(task))
                onComplete(task.isSuccessful, task.exception?.message)
            }
    }

    /**
     * To generate [MUser] model and store it [FirebaseFirestore]
     * @param displayName Display name of the user
     */
    private fun generateUserModel(displayName: String?) {
        val userId = firebaseAuth.currentUser?.uid
        val user = MUser(id = null, userId = userId, displayName = displayName).toMap()
        fireStore.collection("users").add(user)
    }

    /**
     * To generate display name from firebase user
     * @return Display name
     */
    private fun getDisplayName(task: Task<AuthResult>): String? {
        return task.result.user?.email?.substringBefore("@")
    }


}