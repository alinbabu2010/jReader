package com.compose.jreader.ui.screens.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.compose.jreader.R
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.UiState
import com.compose.jreader.ui.components.FadeVisibility
import com.compose.jreader.ui.components.LoaderMessageView
import com.compose.jreader.ui.components.ReaderAppBar
import com.compose.jreader.utils.*

@Composable
fun BookDetailsScreen(
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
            title = "Book Details",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.popBackStack()
        }

    }) {

        Surface(
            modifier = Modifier
                .padding(detailsSurfacePadding)
                .fillMaxSize()
        ) {

            BookDetails(uiState)
            LoaderMessageView(uiState)

        }
    }

}

@Composable
private fun BookDetails(
    uiState: UiState<BookUi>
) {
    FadeVisibility(uiState.data != null) {

        val bookData = uiState.data

        Column(
            modifier = Modifier.padding(detailsColumnPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                modifier = Modifier.padding(bookImageCardPadding),
                shape = CircleShape,
                elevation = bookImageCardElevation
            ) {
                Image(
                    painter = rememberAsyncImagePainter(bookData?.photoUrl),
                    contentDescription = stringResource(R.string.desc_book_image),
                    modifier = Modifier.size(bookImageWidth),
                    contentScale = ContentScale.FillBounds
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = bookData?.title ?: "",
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(detailsSpacerHeight))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {

                if (bookData?.authors?.isNotBlank() == true) {
                    Text(stringResource(R.string.author_name, bookData.authors))
                }

                if (bookData?.pageCount != null) {
                    Text(stringResource(R.string.page_count, bookData.pageCount))
                }

                if (bookData?.categories?.isNotBlank() == true) {
                    Text(
                        text = stringResource(R.string.categories, bookData.categories),
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )
                }

                if (bookData?.publishedDate?.isNotBlank() == true) {
                    Text(
                        text = stringResource(R.string.published_date, bookData.publishedDate),
                        style = MaterialTheme.typography.subtitle1
                    )
                }

            }

            Spacer(modifier = Modifier.height(detailsSpacerHeight))

            if (bookData?.description?.isNotBlank() == true) {

                val displayMetrics = LocalContext.current.resources.displayMetrics

                Surface(
                    modifier = Modifier
                        .height(displayMetrics.heightPixels.dp.times(0.09F))
                        .padding(descriptionSurfacePadding),
                    shape = RectangleShape,
                    border = BorderStroke(descriptionSurfaceBorderStroke, Color.DarkGray)
                ) {
                    LazyColumn(modifier = Modifier.padding(descriptionColPadding)) {
                        item {
                            Text(text = bookData.description)
                        }
                    }
                }

            }

        }
    }
}