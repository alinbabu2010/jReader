package com.compose.jreader.data.repository

import com.compose.jreader.data.model.MUser
import com.compose.jreader.utils.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : LoginRepository {

    /**
     * To login user using [FirebaseAuth] with email and password
     * @param email Email id of the user
     * @param password Password for login
     */
    override fun signIn(
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
    override fun createUser(
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
        fireStore.collection(Constants.USER_DB_NAME).add(user)
    }

    /**
     * To generate display name from firebase user
     * @return Display name
     */
    private fun getDisplayName(task: Task<AuthResult>): String? {
        return task.result.user?.email?.substringBefore(Constants.DELIMITER_AT)
    }

    /**
     * To check a user is already logged based on user email
     * @return True if already logged else false
     */
    override fun isUserLoggedIn(): Boolean {
        return !firebaseAuth.currentUser?.email.isNullOrBlank()
    }

    /**
     * To sign out user from firebase
     */
    override fun signOutUser() = firebaseAuth.signOut()

    /**
     * To get display name to show from fireStore
     */
    override fun displayName(): String? {
        return firebaseAuth.currentUser?.email?.substringBefore(Constants.DELIMITER_AT)
    }


}