package com.compose.jreader.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.compose.jreader.R
import com.compose.jreader.utils.inputFontSize
import com.compose.jreader.utils.inputPadding
import com.compose.jreader.utils.logoTextBottomPadding


@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(bottom = logoTextBottomPadding),
        text = stringResource(R.string.app_name),
        style = MaterialTheme.typography.h3,
        color = Color.Red.copy(alpha = 0.5f)
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    label: String,
    isSingleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardAction: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {}
) {

    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = {
            Text(text = label)
        },
        singleLine = isSingleLine,
        enabled = enabled,
        textStyle = TextStyle(
            fontSize = inputFontSize,
            color = MaterialTheme.colors.onBackground
        ),
        modifier = modifier
            .padding(
                bottom = inputPadding,
                start = inputPadding,
                end = inputPadding
            )
            .fillMaxWidth(),
        keyboardActions = keyboardAction,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon
    )

}