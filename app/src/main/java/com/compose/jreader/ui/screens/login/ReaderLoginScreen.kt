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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.compose.jreader.R
import com.compose.jreader.ui.widgets.EmailInput
import com.compose.jreader.ui.widgets.PasswordInput
import com.compose.jreader.ui.widgets.ReaderLogo
import com.compose.jreader.utils.*

@Composable
fun ReaderLoginScreen(navController: NavHostController) {

    val showLoginForm = rememberSaveable {
        mutableStateOf(false)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ReaderLogo()
            UserForm(isCreateAccount = showLoginForm.value) { email, password ->

            }
            Spacer(modifier = Modifier.height(spacerHeight))
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
                            showLoginForm.value = !showLoginForm.value
                        },
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondaryVariant
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
fun SubmitButton(
    text: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(submitButtonPadding)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape
    ) {

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(submitButtonProgressSize),
                strokeWidth = submitButtonProgressStrokeWidth
            )
        } else {
            Text(
                text = text,
                modifier = Modifier.padding(submitButtonTextPadding)
            )
        }

    }
}
