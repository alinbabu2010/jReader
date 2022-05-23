package com.compose.jreader.ui.screens.home

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.compose.jreader.R
import com.compose.jreader.data.model.Book
import com.compose.jreader.ui.screens.login.ReaderLoginViewModel
import com.compose.jreader.ui.theme.Cyan200
import com.compose.jreader.ui.widgets.ListCard
import com.compose.jreader.ui.widgets.ReaderAppBar
import com.compose.jreader.ui.widgets.TitleSection
import com.compose.jreader.utils.*

@Preview
@Composable
fun ReaderHomeScreen(
    navController: NavHostController = NavHostController(LocalContext.current),
    loginViewModel: ReaderLoginViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        ReaderAppBar(
            stringResource(R.string.app_name),
            navController = navController,
            viewModel = loginViewModel
        )
    }, floatingActionButton = {
        FabContent {

        }
    }) {

        Surface(modifier = Modifier.fillMaxWidth()) {
            HomeContent(navController, loginViewModel)
        }

    }

}

@Composable
fun HomeContent(
    navController: NavHostController,
    loginViewModel: ReaderLoginViewModel
) {

    Column(
        modifier = Modifier.padding(homeContentColumnPadding),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeTitleRow(Modifier.align(Alignment.Start), loginViewModel)
        ReadingRightNowArea(books = Book.getBooks(), navController = navController)
        TitleSection(label = stringResource(R.string.reading_list))
        BookListArea(books = Book.getBooks(), navController = navController)
    }

}

@Composable
fun BookListArea(
    books: List<Book>,
    navController: NavHostController
) {

    HorizontalScrollContainer(books = books) {
        Log.d("TAG", "BookListArea: $it")
    }

}

@Composable
fun HorizontalScrollContainer(
    books: List<Book>,
    onPress: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(hScrollContainerHeight)
            .horizontalScroll(scrollState)
    ) {

        for (book in books) {
            ListCard(book) {
                onPress(it)
            }
        }

    }
}

@Composable
private fun HomeTitleRow(
    modifier: Modifier,
    loginViewModel: ReaderLoginViewModel
) {
    Row(modifier = modifier) {
        TitleSection(label = stringResource(R.string.home_reading_title))
        Spacer(modifier = Modifier.fillMaxWidth(0.7F))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    modifier = Modifier.size(accountIconSize),
                    contentDescription = stringResource(R.string.desc_profile_icon),
                    tint = MaterialTheme.colors.secondaryVariant
                )
            }
            Text(
                text = loginViewModel.displayName ?: stringResource(R.string.not_applicable),
                modifier = Modifier.padding(accountNameTextPadding),
                style = MaterialTheme.typography.overline,
                color = Color.Red,
                fontSize = accountNameTextSize,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Divider()
        }

    }
}

@Composable
fun FabContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = onTap,
        shape = RoundedCornerShape(50),
        backgroundColor = Cyan200
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.desc_icon_add),
            tint = Color.White
        )
    }
}

@Composable
fun ReadingRightNowArea(
    books: List<Book>,
    navController: NavHostController
) {
    ListCard(books.first()) {
        Log.d("TAG", "ReadingRightNowArea: $it")
    }
}