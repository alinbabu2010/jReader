package com.compose.jreader.ui.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.jreader.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderLoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    //val loadingState = MutableStateFlow(LoadingState.IDLE)

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

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

        loginRepository.signIn(email, password) { isSuccess, message ->
            if (isSuccess) {
                navigateToHome()
            } else {
                Log.d("TAG", "createUser: $message")
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

        if (_loading.value == false) {
            _loading.value = true
            loginRepository.createUser(email, password) { isSuccess, message ->
                if (isSuccess) navigateToHome()
                else {
                    Log.d("TAG", "createUser: $message")
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