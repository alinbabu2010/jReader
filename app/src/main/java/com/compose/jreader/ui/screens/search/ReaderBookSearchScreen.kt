package com.compose.jreader.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.compose.jreader.R
import com.compose.jreader.data.model.Book
import com.compose.jreader.ui.components.InputField
import com.compose.jreader.ui.components.ReaderAppBar
import com.compose.jreader.utils.*


@Composable
fun ReaderBookSearchScreen(navController: NavHostController) {

    Scaffold(topBar = {
        ReaderAppBar(
            title = stringResource(R.string.search_books),
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false
        ) {
            navController.popBackStack()
        }

    }) {

        Surface {
            Column {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = searchBarPadding,
                            start = searchBarPadding,
                            end = searchBarPadding
                        )
                ) {

                }
                LazyColumn(
                    Modifier.padding(
                        start = searchLazyColumnPadding,
                        end = searchLazyColumnPadding
                    )
                ) {
                    items(Book.getBooks()) { book ->
                        BookRow(book = book)
                    }
                }
            }
        }


    }

}

@Composable
fun BookRow(book: Book) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = bookRowBottomPadding),
        shape = RectangleShape,
        backgroundColor = Color.White,
        elevation = bookRowElevation
    ) {


        ConstraintLayout(
            modifier = Modifier
                .padding(bookRowPadding)
                .clickable { }
        ) {

            val (image, titleText, authorText, dateText, categoryText) = createRefs()

            AsyncImage(
                model = book.photoUrl,
                contentDescription = stringResource(R.string.desc_book_image),
                modifier = Modifier.constrainAs(image) {
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom
                    )
                    start.linkTo(parent.start)
                    height = Dimension.fillToConstraints
                    width = Dimension.percent(0.15F)
                }
            )

            Text(
                modifier = Modifier.constrainAs(titleText) {
                    linkTo(
                        start = image.end,
                        end = parent.end,
                        startMargin = bookColumnStartEndMargin,
                        endMargin = bookColumnStartEndMargin
                    )
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                },
                text = book.title ?: "",
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.constrainAs(authorText) {
                    linkTo(
                        start = image.end,
                        end = parent.end,
                        startMargin = bookColumnStartEndMargin,
                        endMargin = bookColumnStartEndMargin
                    )
                    top.linkTo(titleText.bottom)
                    width = Dimension.fillToConstraints
                },
                text = stringResource(R.string.author_name, book.authors ?: ""),
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.caption
            )

            Text(
                modifier = Modifier.constrainAs(dateText) {
                    linkTo(
                        start = image.end,
                        end = parent.end,
                        startMargin = bookColumnStartEndMargin,
                        endMargin = bookColumnStartEndMargin
                    )
                    top.linkTo(authorText.bottom)
                    height = Dimension.wrapContent
                    width = Dimension.fillToConstraints
                },
                text = stringResource(R.string.published_date, book.id ?: ""),
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.caption
            )

            Text(
                modifier = Modifier.constrainAs(categoryText) {
                    linkTo(
                        start = image.end,
                        end = parent.end,
                        top = dateText.bottom,
                        bottom = parent.bottom,
                        startMargin = bookColumnStartEndMargin,
                        endMargin = bookColumnStartEndMargin
                    )
                    height = Dimension.wrapContent
                    width = Dimension.fillToConstraints
                },
                text = "[${book.id ?: ""}]",
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.caption
            )

        }

    }

}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = stringResource(R.string.search),
    onSearch: (String) -> Unit = {}
) {

    Column {

        val searchQueryState = rememberSaveable {
            mutableStateOf("")
        }

        val focusManager = LocalFocusManager.current

        val valid = remember(searchQueryState.value) {
            searchQueryState.isValidInput()
        }

        InputField(
            modifier = modifier,
            valueState = searchQueryState,
            label = hint,
            keyboardAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.trimValue())
                searchQueryState.value = ""
                focusManager.clearFocus()
            },
            imeAction = ImeAction.Done
        )

    }

}