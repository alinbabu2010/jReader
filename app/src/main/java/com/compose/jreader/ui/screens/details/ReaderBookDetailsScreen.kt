package com.compose.jreader.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.UiState
import com.compose.jreader.ui.components.FadeVisibility
import com.compose.jreader.ui.components.LoaderMessageView
import com.compose.jreader.ui.components.ReaderAppBar
import com.compose.jreader.utils.detailsColumnPadding
import com.compose.jreader.utils.detailsSurfacePadding

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

            DetailsColumn(uiState)
            LoaderMessageView(uiState)

        }
    }

}

@Composable
private fun DetailsColumn(
    uiState: UiState<BookUi>
) {
    FadeVisibility(uiState.data != null) {
        Column(
            modifier = Modifier.padding(detailsColumnPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Book id: ${uiState.data?.title}")
        }
    }
}