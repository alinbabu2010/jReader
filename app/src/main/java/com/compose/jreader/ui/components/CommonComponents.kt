package com.compose.jreader.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.compose.jreader.R
import com.compose.jreader.ui.navigation.ReaderScreens
import com.compose.jreader.ui.screens.login.ReaderLoginViewModel
import com.compose.jreader.ui.theme.Green400
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

@Composable
fun ReaderAppBar(
    title: String,
    showProfile: Boolean = true,
    navController: NavController,
    viewModel: ReaderLoginViewModel
) {

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile) {
                    Icon(
                        painter = painterResource(R.drawable.ic_logo),
                        contentDescription = stringResource(R.string.desc_reader_logo),
                        modifier = Modifier
                            .clip(RoundedCornerShape(appBarIconCorner))
                            .scale(0.9F)
                    )
                }
                Text(
                    text = title,
                    color = Color.Red.copy(0.7F),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = appBarTitleFontSize
                    )
                )
                Spacer(modifier = Modifier.width(appBarSpacerWidth))
            }
        },
        actions = {
            IconButton(onClick = {
                viewModel.signOut().run {
                    navController.navigate(ReaderScreens.LoginScreen.name)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = stringResource(R.string.desc_logout_icon),
                    tint = Green400
                )
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

