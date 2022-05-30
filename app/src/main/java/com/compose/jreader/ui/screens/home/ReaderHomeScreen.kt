package com.compose.jreader.ui.screens.home

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.ui.components.ListCard
import com.compose.jreader.ui.components.LoaderMessageView
import com.compose.jreader.ui.components.ReaderAppBar
import com.compose.jreader.ui.components.TitleSection
import com.compose.jreader.ui.model.UiState
import com.compose.jreader.ui.navigation.ReaderScreens
import com.compose.jreader.ui.screens.login.ReaderLoginViewModel
import com.compose.jreader.ui.theme.Cyan200
import com.compose.jreader.utils.*

@Preview
@Composable
fun ReaderHomeScreen(
    navController: NavHostController = NavHostController(LocalContext.current),
    loginViewModel: ReaderLoginViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        ReaderAppBar(
            stringResource(R.string.app_name),
            navController = navController,
            viewModel = loginViewModel
        )
    }, floatingActionButton = {
        FabContent {
            navController.navigate(ReaderScreens.SearchScreen.name)
        }
    }) {

        val scrollState = rememberScrollState()

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            HomeContent(navController, loginViewModel, homeViewModel)
        }

    }

}

@Composable
fun HomeContent(
    navController: NavHostController,
    loginViewModel: ReaderLoginViewModel,
    homeViewModel: HomeViewModel,
) {

    Column(
        modifier = Modifier.padding(homeContentColumnPadding),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeTitleRow(Modifier.align(Alignment.Start), loginViewModel)
        ReadingRightNowArea(bookUis = BookUi.getBooks(), navController = navController)
        TitleSection(label = stringResource(R.string.reading_list))
        val uiState = homeViewModel.listOfBooks.collectAsState()
        BookListArea(uiState, navController = navController)
    }

}

@Composable
fun BookListArea(
    state: State<UiState<List<BookUi>>>,
    navController: NavHostController
) {
    val bookList = state.value.data
    if (bookList == null) {
        LoaderMessageView(state.value, "")
    } else {
        HorizontalScrollContainer(bookUis = bookList) {
            Log.d("TAG", "BookListArea: $it")
        }
    }
}

@Composable
fun HorizontalScrollContainer(
    bookUis: List<BookUi>,
    onPress: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(hScrollContainerHeight)
            .horizontalScroll(scrollState)
    ) {

        for (book in bookUis) {
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
    bookUis: List<BookUi>,
    navController: NavHostController
) {
    ListCard(bookUis.first()) {
        Log.d("TAG", "ReadingRightNowArea: $it")
    }
}