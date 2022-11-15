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

    private val _loginUiState = MutableStateFlow(LoginUiState(false, ""))
    val loginUiState = _loginUiState.asStateFlow()

    private val _displayName = loginRepository.displayName()
    val displayName get() = _displayName

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

        if (!loginUiState.value.isLoading) {
            _loginUiState.value = _loginUiState.value.copy(isLoading = true)
            loginRepository.signIn(email, password) { isSuccess, message ->
                if (isSuccess) {
                    navigateToHome()
                } else {
                    _loginUiState.value = _loginUiState.value.copy(message = message ?: "")
                }
                _loginUiState.value = _loginUiState.value.copy(isLoading = false)
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

        if (!loginUiState.value.isLoading) {
            _loginUiState.value = _loginUiState.value.copy(isLoading = true)
            loginRepository.createUser(email, password) { isSuccess, message ->
                if (isSuccess) navigateToHome()
                else {
                    _loginUiState.value = _loginUiState.value.copy(message = message ?: "")
                }
                _loginUiState.value = _loginUiState.value.copy(isLoading = false)
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

data class LoginUiState(
    val isLoading: Boolean,
    val message: String
)