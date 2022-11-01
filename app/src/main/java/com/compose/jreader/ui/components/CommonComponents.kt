package com.compose.jreader.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import com.compose.jreader.R
import com.compose.jreader.ui.theme.Green400
import com.compose.jreader.ui.theme.Red700
import com.compose.jreader.utils.*


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
    isNoteField: Boolean = false,
    trailingIcon: @Composable () -> Unit = {}
) {

    val value = if (isNoteField) {
        valueState.value.ifEmpty { stringResource(R.string.default_note) }
    } else valueState.value

    OutlinedTextField(
        value = value,
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

@Composable
fun ReaderAppBar(
    title: String,
    showProfile: Boolean = true,
    icon: ImageVector? = null,
    onLogout: () -> Unit = {},
    onBackArrowClick: () -> Unit = {}
) {

    TopAppBar(
        title = {
            ConstraintLayout(Modifier.fillMaxWidth()) {
                val (navIcon, text) = createRefs()
                Icon(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = stringResource(R.string.desc_reader_logo),
                    modifier = Modifier
                        .clip(RoundedCornerShape(appBarIconCorner))
                        .scale(0.9F)
                        .constrainAs(navIcon) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(text.start)
                            visibility = if (showProfile) Visibility.Visible else Visibility.Gone
                        }
                )
                Text(
                    text = title,
                    color = Red700,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = appBarTitleFontSize
                    ),
                    modifier = Modifier.constrainAs(text) {
                        if (showProfile) start.linkTo(parent.start)
                        else start.linkTo(parent.start, margin = appBarTitleMarginStart)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }
        },
        actions = {
            if (showProfile) {
                IconButton(onClick = {
                    onLogout()
                }) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = stringResource(R.string.desc_logout_icon),
                        tint = Green400
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBackArrowClick.invoke() }) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(R.string.desc_back_icon),
                        tint = Red700
                    )
                }
            }
        },
        backgroundColor = Color.Transparent,
        elevation = appBarElevation
    )

}

@Composable
fun TitleSection(
    modifier: Modifier = Modifier,
    label: String
) {
    Surface(
        modifier = modifier.padding(
            start = titleSecSurfaceStartPadding,
            top = titleSecSurfaceTopPadding
        )
    ) {
        Column {
            Text(
                text = label,
                fontSize = titleSecFontSize,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left
            )
        }
    }
}


@Composable
fun ShowAlertDialog(
    title: String,
    message: String,
    showState: MutableState<Boolean>,
    onPositiveAction: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            showState.value = false
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(onClick = { onPositiveAction() }) {
                Text(text = stringResource(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = { showState.value = false }) {
                Text(text = stringResource(R.string.no))
            }
        })

}

