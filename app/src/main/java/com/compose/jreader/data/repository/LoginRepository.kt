package com.compose.jreader.data.repository

interface LoginRepository {

    /**
     * To login user using FirebaseAuth with email and password
     * @param email Email id of the user
     * @param password Password for login
     */
    fun signIn(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    )

    /**
     * To create user using FirebaseAuth with email and password
     * @param email Email id of the new user
     * @param password Password for user creation
     */
    fun createUser(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    )

    /**
     * To check a user is already logged based on user email
     * @return True if already logged else false
     */
    fun isUserLoggedIn(): Boolean

    /**
     * To sign out user from firebase
     */
    fun signOutUser()

    /**
     * To get display name to show from fireStore
     */
    fun displayName(): String?
}