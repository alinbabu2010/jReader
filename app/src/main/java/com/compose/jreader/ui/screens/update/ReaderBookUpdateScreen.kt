package com.compose.jreader.ui.screens.update

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.compose.jreader.R
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.ui.components.*
import com.compose.jreader.ui.model.UiState
import com.compose.jreader.ui.screens.details.DetailsViewModel
import com.compose.jreader.utils.*

@Composable
fun ReaderBookUpdateScreen(
    navController: NavHostController,
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val uiState by remember {
        viewModel.getBookInfo(bookId)
        viewModel.bookInfo
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
            UpdateComposable(uiState)
            LoaderMessageView(uiState, stringResource(R.string.no_info_found))
        }

    }

}

@Composable
fun UpdateComposable(uiState: UiState<BookUi>) {

    FadeVisibility(uiState.data != null) {
        Column(
            modifier = Modifier.padding(updateColumnPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ShowBookInfo(uiState)
            EnterThoughts(
                modifier = Modifier
                    .padding(thoughtsTextPadding)
                    .height(thoughtsTextHeight)
            ) {

            }
            StatusButton()
            RateBook()
            UpdateButtons()

        }

    }
}

@Composable
fun UpdateButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = updateButtonTopPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        RoundedButton(label = stringResource(R.string.update)) {

        }

        RoundedButton(label = stringResource(R.string.delete)) {

        }

    }
}

@Composable
fun RateBook() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.rating))


    }
}

@Composable
fun StatusButton() {
    Row(
        modifier = Modifier.padding(
            end = statusButtonPadding,
            start = statusButtonPadding
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Start reading")
        }

        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Mark as Read")
        }

    }
}

@Composable
fun EnterThoughts(modifier: Modifier, onSubmit: (String) -> Unit) {

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val noteState = rememberSaveable {
        mutableStateOf("")
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
            noteState.value = ""
            focusManager.clearFocus()
        }
    )
}

@Composable
private fun ShowBookInfo(uiState: UiState<BookUi>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        elevation = updateBookInfoElevation
    ) {
        ConstraintLayout(Modifier.padding(updateConstraintLayoutPadding)) {

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
                    .height(bookImageHeight)
                    .width(bookImageWidth)
                    .padding(bookImagePadding),
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
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = data?.authors ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = data?.publishedDate ?: "")
            }


        }
    }
}

