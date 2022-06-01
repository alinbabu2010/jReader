package com.compose.jreader.ui.screens.update

import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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

@ExperimentalComposeUiApi
@Composable
fun ReaderBookUpdateScreen(
    navController: NavHostController,
    bookId: String,
    viewModel: UpdateViewModel = hiltViewModel()
) {

    val uiState by produceState<UiState<BookUi>>(
        initialValue = UiState(isLoading = true)
    ) {
        value = viewModel.getBook(bookId)
    }

    Scaffold(topBar = {
        ReaderAppBar(
            title = stringResource(R.string.update_book),
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false
        ) {
            navController.popBackStack()
        }
    }) {

        Surface(
            modifier = Modifier
                .padding(updateSurfacePadding)
                .fillMaxSize()

        ) {
            UpdateComposable(uiState, viewModel)
            LoaderMessageView(uiState, stringResource(R.string.no_info_found))
        }

    }

}

@ExperimentalComposeUiApi
@Composable
fun UpdateComposable(uiState: UiState<BookUi>, viewModel: UpdateViewModel) {

    FadeVisibility(uiState.data != null) {

        var ratingValue by rememberSaveable {
            mutableStateOf(0)
        }

        val defaultNote = stringResource(R.string.default_note)

        var notes by rememberSaveable {
            mutableStateOf(defaultNote)
        }

        val isStartedReading = rememberSaveable {
            mutableStateOf(false)
        }

        val isFinishedReading = rememberSaveable {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier.padding(updateColumnPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ShowBookInfo(uiState) {

            }
            EnterThoughts(
                modifier = Modifier
                    .padding(thoughtsTextPadding)
                    .height(thoughtsTextHeight)
            ) {
                notes = it
            }
            StatusButton(uiState.data, isStartedReading, isFinishedReading)

            uiState.data?.rating?.let {
                RateBar(it) { rating ->
                    ratingValue = rating
                }
            }

            UpdateButtons { isUpdate ->
                if (isUpdate) {
                    val bookUpdateValue = BookUpdateValue(
                        notes,
                        isFinishedReading.value,
                        isStartedReading.value,
                        ratingValue
                    )
                    viewModel.updateBook(bookUpdateValue) {

                    }
                }

            }

        }

    }
}

@Composable
fun UpdateButtons(onButtonClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = updateButtonTopPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        RoundedButton(label = stringResource(R.string.update)) {
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
                                    if (ratingState != index || ratingState == 0) {
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
    isStartedReading: MutableState<Boolean>,
    isFinishedReading: MutableState<Boolean>
) {

    Row(
        modifier = Modifier.padding(
            end = statusButtonPadding,
            start = statusButtonPadding
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        if (book?.startedReading == null) {
            TextButton(
                onClick = { isStartedReading.value = true },
                enabled = book?.startedReading == null
            ) {
                if (isStartedReading.value) {
                    Text(
                        text = stringResource(R.string.started_reading),
                        modifier = Modifier.alpha(0.6f),
                        color = Red500
                    )
                } else Text(text = stringResource(R.string.start_reading))
            }
        } else {
            Text(
                text = stringResource(
                    R.string.read_started_date,
                    book.startedReading.toString()
                )
            )
        }

        TextButton(
            onClick = { isFinishedReading.value = true },
            enabled = book?.finishedReading == null
        ) {
            if (book?.finishedReading == null) {
                if (isFinishedReading.value) {
                    Text(text = stringResource(R.string.finished_reading))
                } else Text(text = stringResource(R.string.mark_as_read))
            } else {
                Text(
                    text = stringResource(
                        R.string.read_finished_date,
                        book.finishedReading.toString()
                    )
                )
            }
        }

    }
}

@Composable
fun EnterThoughts(modifier: Modifier, onSubmit: (String) -> Unit) {

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val defaultNote = stringResource(R.string.default_note)

    val noteState = rememberSaveable {
        mutableStateOf(defaultNote)
    }

    val validInput by remember(noteState.value) {
        mutableStateOf(noteState.isValidInput())
    }

    InputField(
        modifier = modifier,
        valueState = noteState,
        label = stringResource(R.string.notes_input_label),
        imeAction = ImeAction.Done,
        isSingleLine = false,
        keyboardAction = KeyboardActions {
            if (!validInput) {
                context.showToast(R.string.text_error)
                return@KeyboardActions
            }
            onSubmit(noteState.trimValue())
            focusManager.clearFocus()
        }
    )
}

@Composable
private fun ShowBookInfo(uiState: UiState<BookUi>, onInfoClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        elevation = updateBookInfoElevation
    ) {
        ConstraintLayout(
            Modifier
                .padding(updateConstraintLayoutPadding)
                .clickable {
                    onInfoClick()
                }) {

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
                    .width(imageWidth)
                    .padding(imagePadding),
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

