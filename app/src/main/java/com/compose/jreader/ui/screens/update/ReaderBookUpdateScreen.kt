package com.compose.jreader.ui.screens.update

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.compose.jreader.R
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.BookUpdateValue
import com.compose.jreader.ui.components.*
import com.compose.jreader.ui.model.UiState
import com.compose.jreader.ui.theme.Grey100
import com.compose.jreader.ui.theme.Red500
import com.compose.jreader.ui.theme.Yellow100
import com.compose.jreader.utils.*

private var bookID = ""

@ExperimentalComposeUiApi
@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun ReaderBookUpdateScreen(
    bookId: String,
    viewModel: UpdateViewModel,
    onNavigateBack: () -> Unit
) {

    bookID = bookId

    val uiState by produceState<UiState<BookUi>>(
        initialValue = UiState(isLoading = true)
    ) {
        value = viewModel.getBook(bookId)
    }

    Scaffold(topBar = {
        ReaderAppBar(
            title = stringResource(R.string.update_book),
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            onBackArrowClick = onNavigateBack
        )
    }) {

        Surface(
            modifier = Modifier
                .padding(updateSurfacePadding)
                .fillMaxSize()

        ) {
            UpdateComposable(uiState, viewModel, onNavigateBack)
            LoaderMessageView(uiState, stringResource(R.string.no_info_found))
        }

    }

}

@ExperimentalComposeUiApi
@Composable
fun UpdateComposable(
    uiState: UiState<BookUi>,
    viewModel: UpdateViewModel,
    onNavigateBack: () -> Unit,
) {

    FadeVisibility(uiState.data != null) {

        val bookInfo = uiState.data as BookUi

        var ratingValue by rememberSaveable {
            mutableStateOf(bookInfo.rating)
        }

        val context = LocalContext.current

        val notes = rememberSaveable {
            mutableStateOf(bookInfo.notes)
        }

        var isStartedReading by rememberSaveable {
            mutableStateOf(false)
        }

        var isFinishedReading by rememberSaveable {
            mutableStateOf(false)
        }

        var showDeleteConfirmation by rememberSaveable {
            mutableStateOf(false)
        }

        val isInfoChanged by remember(
            notes.value,
            isFinishedReading,
            isStartedReading,
            ratingValue
        ) {
            val bookUpdateValue = BookUpdateValue(
                notes.value,
                isFinishedReading,
                isStartedReading,
                ratingValue
            )
            mutableStateOf(viewModel.isValidForUpdate(bookUpdateValue))
        }

        Column(
            modifier = Modifier.padding(updateColumnPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ShowBookInfo(uiState) {

            }
            EnterThoughts(
                notes = notes.value.ifEmpty { stringResource(R.string.default_note) },
                modifier = Modifier
                    .padding(thoughtsTextPadding)
                    .height(thoughtsTextHeight)
            ) {
                notes.value = it
            }
            StatusButton(
                uiState.data,
                isStartedReading,
                isFinishedReading,
                onStartedClick = { isStartedReading = true },
                onFinished = { isFinishedReading = true }
            )

            RateBar(bookInfo.rating) { rating ->
                ratingValue = rating
            }

            UpdateButtons(isInfoChanged) { isUpdate ->

                if (isUpdate) {
                    viewModel.updateBook { isSuccess ->
                        if (isSuccess == true) {
                            context.showToast(R.string.update_success_msg)
                            onNavigateBack()
                        }
                        if (isSuccess == false) {
                            context.showToast(R.string.update_failure_msg)
                        }
                    }
                } else {
                    showDeleteConfirmation = true
                }

            }

            if (showDeleteConfirmation) {
                ShowAlertDialog(
                    title = stringResource(R.string.delete_book),
                    message = stringResource(R.string.delete_dialog_msg),
                    onDismiss = { showDeleteConfirmation = false }
                ) {
                    viewModel.deleteBook(bookID) { isSuccess ->
                        if (isSuccess) {
                            showDeleteConfirmation = false
                            context.showToast(R.string.delete_success_msg)
                            onNavigateBack()
                        } else {
                            context.showToast(R.string.delete_failure_msg)
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun UpdateButtons(isUpdateEnabled: Boolean, onButtonClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = updateButtonTopPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        RoundedButton(isEnabled = isUpdateEnabled, label = stringResource(R.string.update)) {
            onButtonClick(true)
        }

        RoundedButton(label = stringResource(R.string.delete)) {
            onButtonClick(false)
        }

    }
}

@ExperimentalComposeUiApi
@Composable
fun RateBar(rating: Int, onRatingClick: (Int) -> Unit) {

    var ratingState by remember {
        mutableStateOf(rating)
    }

    var isSelected by remember {
        mutableStateOf(false)
    }

    val starSize by animateDpAsState(
        targetValue = if (isSelected) starSizeSelected else starSizeNotSelected,
        spring(Spring.DampingRatioLowBouncy)
    )

    Column(
        modifier = Modifier.padding(top = rateBarTopPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.rating),
            modifier = Modifier.padding(bottom = ratingTextBottomPadding)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            for (index in 1..5) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(R.string.desc_star_icon),
                    modifier = Modifier
                        .size(starSize)
                        .pointerInteropFilter {
                            when (it.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    if (ratingState != index) {
                                        isSelected = true
                                        onRatingClick(index)
                                        ratingState = index
                                    }
                                }

                                MotionEvent.ACTION_UP -> {
                                    isSelected = false
                                }
                            }
                            true
                        },
                    tint = if (index <= ratingState) Yellow100 else Grey100
                )
            }
        }

    }
}

@Composable
fun StatusButton(
    book: BookUi?,
    isStartedReading: Boolean,
    isFinishedReading: Boolean,
    onStartedClick: () -> Unit,
    onFinished: () -> Unit
) {

    Row(
        modifier = Modifier
            .padding(
                end = statusButtonPadding,
                start = statusButtonPadding
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        TextButton(
            onClick = onStartedClick,
            enabled = book?.startedReading == null
        ) {
            if (book?.startedReading == null) {
                if (isStartedReading) {
                    Text(
                        text = stringResource(R.string.started_reading),
                        modifier = Modifier.alpha(0.6f),
                        color = Red500
                    )
                } else Text(text = stringResource(R.string.start_reading))
            } else {
                Text(
                    text = stringResource(
                        R.string.read_started_date,
                        book.startedReading?.formatDate().toString()
                    )
                )
            }
        }


        TextButton(
            onClick = onFinished,
            enabled = book?.finishedReading == null
        ) {
            if (book?.finishedReading == null) {
                if (isFinishedReading) {
                    Text(text = stringResource(R.string.finished_reading))
                } else Text(text = stringResource(R.string.mark_as_read))
            } else {
                Text(
                    text = stringResource(
                        R.string.read_finished_date,
                        book.finishedReading?.formatDate().toString()
                    )
                )
            }
        }

    }
}

@Composable
fun EnterThoughts(notes: String, modifier: Modifier, onSubmit: (String) -> Unit) {

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    var thoughts by remember {
        mutableStateOf(notes)
    }

    OutlinedTextField(
        value = thoughts,
        onValueChange = {
            thoughts = it
            onSubmit(thoughts.trim())
        },
        modifier = modifier
            .padding(
                bottom = inputPadding,
                start = inputPadding,
                end = inputPadding
            )
            .fillMaxWidth(),
        label = {
            Text(text = stringResource(id = R.string.notes_input_label))
        },
        singleLine = false,
        textStyle = TextStyle(
            fontSize = inputFontSize,
            color = MaterialTheme.colors.onBackground
        ),
        keyboardActions = KeyboardActions {
            if (thoughts.trim().isBlank()) {
                context.showToast(R.string.text_error)
                return@KeyboardActions
            }
            onSubmit(thoughts.trim())
            focusManager.clearFocus()
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        )
    )
}

@Composable
private fun ShowBookInfo(uiState: UiState<BookUi>, onInfoClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(updateConstraintLayoutPadding),
        shape = CircleShape,
        elevation = updateBookInfoElevation
    ) {
        ConstraintLayout(
            Modifier.clickable { onInfoClick() }
        ) {

            val data = uiState.data

            val (bookImage, bookInfoText) = createRefs()

            Image(
                modifier = Modifier
                    .constrainAs(bookImage) {
                        linkTo(
                            top = parent.top,
                            bottom = parent.bottom
                        )
                        start.linkTo(parent.start, imageMarginStart)
                    }
                    .height(imageHeight)
                    .width(imageWidth),
                painter = rememberAsyncImagePainter(
                    model = data?.photoUrl,
                    contentScale = ContentScale.FillBounds
                ), contentDescription = stringResource(R.string.desc_book_image)
            )

            Column(modifier = Modifier.constrainAs(bookInfoText) {
                linkTo(
                    start = bookImage.end,
                    end = parent.end,
                    top = parent.top,
                    bottom = parent.bottom,
                    startMargin = infoTextMargin,
                    topMargin = infoTextMargin,
                    endMargin = infoTextMargin,
                    bottomMargin = infoTextMargin
                )
                width = Dimension.fillToConstraints
            }) {
                Text(
                    text = data?.title ?: "",
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = data?.authors ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = data?.publishedDate ?: "",
                    style = MaterialTheme.typography.body2
                )
            }


        }
    }
}

