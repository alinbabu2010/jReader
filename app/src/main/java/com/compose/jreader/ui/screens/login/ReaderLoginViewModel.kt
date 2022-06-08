package com.compose.jreader.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.jreader.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderLoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    val displayName = loginRepository.displayName()

    /**
     * To login user using email and password
     * @param email Email id of the user
     * @param password Password for login
     * @param navigateToHome Callback function to be executed after login process
     */
    fun loginUser(
        email: String,
        password: String,
        navigateToHome: () -> Unit
    ) = viewModelScope.launch {

        if (!_loading.value) {
            _loading.value = true
            loginRepository.signIn(email, password) { isSuccess, message ->
                if (isSuccess) {
                    navigateToHome()
                } else {
                    _message.value = message ?: ""
                }
                _loading.value = false
            }
        }

    }

    /**
     * To create user using email and password
     * @param email Email id of the user
     * @param password Password for login
     * @param navigateToHome Callback function to be executed after login process
     */
    fun createUser(
        email: String,
        password: String,
        navigateToHome: () -> Unit
    ) = viewModelScope.launch {

        if (!_loading.value) {
            _loading.value = true
            loginRepository.createUser(email, password) { isSuccess, message ->
                if (isSuccess) navigateToHome()
                else {
                    _message.value = message ?: ""
                }
                _loading.value = false
            }
        }

    }

    /**
     * To check a user is already logged in
     * @return True if already logged else false
     */
    fun isLoggedIn() = loginRepository.isUserLoggedIn()

    /**
     * To sign out user
     */
    fun signOut() = loginRepository.signOutUser()


}