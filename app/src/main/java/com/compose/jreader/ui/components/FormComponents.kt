package com.compose.jreader.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.compose.jreader.R

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: String,
    onChange: (String) -> Unit,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onKeyboardAction: KeyboardActions = KeyboardActions.Default,
) {
    InputField(
        modifier = modifier,
        value = email,
        onValueChange = onChange,
        label = stringResource(R.string.email),
        enabled = enabled,
        imeAction = imeAction,
        keyboardAction = onKeyboardAction,
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    password: String,
    onChange: (String) -> Unit,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    showPassword: Boolean,
    onPasswordVisibility: () -> Unit,
    onKeyboardAction: KeyboardActions = KeyboardActions.Default,
) {

    val visualTransformation = if (showPassword) VisualTransformation.None
    else PasswordVisualTransformation()

    InputField(
        modifier = modifier,
        value = password,
        onValueChange = onChange,
        label = stringResource(R.string.password),
        enabled = enabled,
        imeAction = imeAction,
        keyboardAction = onKeyboardAction,
        keyboardType = KeyboardType.Password,
        visualTransformation = visualTransformation
    ) {
        IconButton(onClick = onPasswordVisibility) {
            Icon(
                imageVector = if (showPassword) Icons.Default.VisibilityOff
                else Icons.Default.Visibility,
                contentDescription = "",
                tint = Color.Gray
            )
        }
    }

}
