package com.compose.jreader.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.jreader.R
import com.compose.jreader.ui.components.EmailInput
import com.compose.jreader.ui.components.PasswordInput
import com.compose.jreader.ui.components.ReaderLogo
import com.compose.jreader.utils.formColumnHeight
import com.compose.jreader.utils.formInputCriteriaTextPadding
import com.compose.jreader.utils.isValidInput
import com.compose.jreader.utils.loginErrorTextHPadding
import com.compose.jreader.utils.loginRowPadding
import com.compose.jreader.utils.loginRowTextStartPadding
import com.compose.jreader.utils.spacer1Height
import com.compose.jreader.utils.spacer2Height
import com.compose.jreader.utils.submitButtonPadding
import com.compose.jreader.utils.submitButtonProgressSize
import com.compose.jreader.utils.submitButtonProgressStrokeWidth
import com.compose.jreader.utils.submitButtonTextPadding

@Composable
fun ReaderLoginScreen(
    loginViewModel: ReaderLoginViewModel,
    onNavigateToHomeScreen: () -> Unit
) {

    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }

    val uiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()


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
                        onNavigateToHomeScreen()
                    }
                } else {
                    loginViewModel.createUser(email, password) {
                        onNavigateToHomeScreen()
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

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }

    val passwordFocusRequest = FocusRequester.Default

    val focusManager = LocalFocusManager.current

    val isValidInputs = remember(email, password) {
        email.isValidInput() && password.isValidInput()
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
            email = email,
            onChange = { email = it },
            onKeyboardAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            },
            enabled = !loading
        )
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            password = password,
            onChange = { password = it },
            enabled = !loading,
            imeAction = ImeAction.Done,
            showPassword = showPassword,
            onPasswordVisibility = { showPassword = !showPassword },
            onKeyboardAction = KeyboardActions {
                if (!isValidInputs) return@KeyboardActions
                onDone(email.trim(), password.trim())
                focusManager.clearFocus()
            }
        )
        SubmitButton(
            text = stringResource(
                if (isCreateAccount) R.string.create_account
                else R.string.login
            ),
            loading = loading,
            validInputs = isValidInputs
        ) {
            onDone(email.trim(), password.trim())
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
