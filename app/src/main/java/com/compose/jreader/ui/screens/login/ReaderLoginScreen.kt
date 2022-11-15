package com.compose.jreader.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.compose.jreader.R
import com.compose.jreader.ui.components.EmailInput
import com.compose.jreader.ui.components.PasswordInput
import com.compose.jreader.ui.components.ReaderLogo
import com.compose.jreader.ui.navigation.ReaderScreens
import com.compose.jreader.utils.*

@Composable
fun ReaderLoginScreen(
    navController: NavHostController,
    loginViewModel: ReaderLoginViewModel = hiltViewModel()
) {

    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }

    val uiState = loginViewModel.loginUiState.collectAsState().value


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ReaderLogo()
            UserForm(
                loading = uiState.isLoading,
                isCreateAccount = !showLoginForm.value
            ) { email, password ->
                if (showLoginForm.value) {
                    loginViewModel.loginUser(email, password) {
                        navController.navigate(ReaderScreens.HomeScreen.name)
                    }
                } else {
                    loginViewModel.createUser(email, password) {
                        navController.navigate(ReaderScreens.HomeScreen.name)
                    }
                }
            }
            Spacer(modifier = Modifier.height(spacer1Height))
            Row(
                modifier = Modifier.padding(loginRowPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(
                        if (showLoginForm.value) R.string.new_user
                        else R.string.already_user
                    )
                )
                Text(
                    stringResource(
                        if (showLoginForm.value) R.string.sign_up
                        else R.string.login
                    ),
                    modifier = Modifier
                        .padding(start = loginRowTextStartPadding)
                        .clickable {
                            if (!uiState.isLoading) showLoginForm.value = !showLoginForm.value
                        },
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondaryVariant
                )

            }
            if (uiState.message.isNotBlank()) {
                Spacer(modifier = Modifier.height(spacer2Height))
                Text(
                    modifier = Modifier.padding(horizontal = loginErrorTextHPadding),
                    text = uiState.message,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.subtitle1
                )
            }

        }
    }

}

@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { _, _ -> }
) {

    val emailState = rememberSaveable {
        mutableStateOf("")
    }

    val passwordState = rememberSaveable {
        mutableStateOf("")
    }

    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }

    val passwordFocusRequest = FocusRequester.Default

    val focusManager = LocalFocusManager.current

    val validInputState = remember(emailState.value, passwordState.value) {
        emailState.isValidInput() && passwordState.isValidInput()
    }

    Column(
        modifier = Modifier
            .height(formColumnHeight)
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val infoText = if (isCreateAccount)
            stringResource(R.string.input_criteria_message) else ""
        Text(
            text = infoText,
            modifier = Modifier.padding(formInputCriteriaTextPadding)
        )
        EmailInput(
            emailState = emailState,
            onKeyboardAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            },
            enabled = !loading
        )
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = passwordState,
            enabled = !loading,
            imeAction = ImeAction.Done,
            passwordVisibility = passwordVisibility,
            onKeyboardAction = KeyboardActions {
                if (!validInputState) return@KeyboardActions
                onDone(emailState.trimValue(), passwordState.trimValue())
                focusManager.clearFocus()
            }
        )
        SubmitButton(
            text = stringResource(
                if (isCreateAccount) R.string.create_account
                else R.string.login
            ),
            loading = loading,
            validInputs = validInputState
        ) {
            onDone(emailState.trimValue(), passwordState.trimValue())
            focusManager.clearFocus()
        }
    }

}

@Composable
private fun SubmitButton(
    text: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            if (!loading) onClick()
        },
        modifier = Modifier
            .padding(submitButtonPadding)
            .fillMaxWidth(),
        enabled = validInputs,
        shape = CircleShape
    ) {

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(submitButtonProgressSize),
                strokeWidth = submitButtonProgressStrokeWidth,
                color = Color.White
            )
        } else {
            Text(
                text = text,
                modifier = Modifier.padding(submitButtonTextPadding)
            )
        }

    }
}
