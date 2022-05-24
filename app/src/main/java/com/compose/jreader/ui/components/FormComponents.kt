package com.compose.jreader.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.compose.jreader.R

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onKeyboardAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        label = stringResource(R.string.email),
        enabled = enabled,
        imeAction = imeAction,
        keyboardAction = onKeyboardAction
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    passwordState: MutableState<String>,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    passwordVisibility: MutableState<Boolean>,
    onKeyboardAction: KeyboardActions = KeyboardActions.Default
) {

    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None
    else PasswordVisualTransformation()

    InputField(
        modifier = modifier,
        valueState = passwordState,
        label = stringResource(R.string.password),
        enabled = enabled,
        imeAction = imeAction,
        keyboardAction = onKeyboardAction,
        keyboardType = KeyboardType.Password,
        visualTransformation = visualTransformation
    ) {
        PasswordVisibility(passwordVisibility = passwordVisibility)
    }
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible }) {
        Icons.Default.Close
    }
}
