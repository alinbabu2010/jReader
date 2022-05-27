package com.compose.jreader.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.compose.jreader.R
import com.compose.jreader.ui.model.UiState
import com.compose.jreader.utils.containerTextPadding
import com.compose.jreader.utils.progressBarSize

@Composable
fun <T> LoaderMessageView(uiState: UiState<T>) {

    Row(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(progressBarSize)
            )
        } else {
            Text(
                text = if (uiState.isEmpty) stringResource(R.string.no_books_found)
                else uiState.message ?: "",
                modifier = Modifier.padding(containerTextPadding),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
        }
    }

}

@Composable
fun FadeVisibility(
    isVisible: Boolean,
    content: @Composable () -> Unit
) {

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        content()
    }

}