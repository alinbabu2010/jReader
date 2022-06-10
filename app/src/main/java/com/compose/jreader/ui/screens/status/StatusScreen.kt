package com.compose.jreader.ui.screens.status

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.compose.jreader.R
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.ui.components.ReaderAppBar
import com.compose.jreader.ui.screens.home.HomeViewModel
import com.compose.jreader.ui.screens.login.ReaderLoginViewModel
import com.compose.jreader.ui.theme.Green400
import com.compose.jreader.utils.*

@Composable
fun StatusScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    loginViewModel: ReaderLoginViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        ReaderAppBar(
            title = stringResource(R.string.book_status),
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false
        ) {
            navController.popBackStack()
        }
    }) {

        Surface(modifier = Modifier.padding(horizontal = statusSurfaceHPadding)) {

            Column {

                UserInfo(loginViewModel)
                UserReadingInfo(homeViewModel)
                Divider(modifier = Modifier.padding(top = statusDividerTopPadding))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentPadding = PaddingValues(statusLazyColContentPadding)
                ) {
                    items(homeViewModel.getReadBooks()) { book ->
                        BookRow(book)
                    }
                }

            }

        }

    }

}

@Composable
private fun UserReadingInfo(homeViewModel: HomeViewModel) {

    val resources = LocalContext.current.resources

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(statusCardPadding),
        shape = CircleShape,
        elevation = statusCardElevation
    ) {

        Column(
            modifier = Modifier.padding(
                vertical = statusCardRowVPadding,
                horizontal = statusCardRowHPadding
            ),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.your_status),
                style = MaterialTheme.typography.h5
            )
            Divider()
            Text(
                text = resources.getQuantityString(
                    R.plurals.reading_count,
                    homeViewModel.getReadingBookCount(),
                    homeViewModel.getReadingBookCount()
                )
            )
            Text(
                text = resources.getQuantityString(
                    R.plurals.read_count,
                    homeViewModel.getReadBookCount(),
                    homeViewModel.getReadBookCount()
                )
            )
        }

    }

}

@Composable
private fun UserInfo(loginViewModel: ReaderLoginViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(statusRowHeight)
            .padding(statusRowPadding)
    ) {
        Icon(
            imageVector = Icons.Sharp.Person,
            contentDescription = stringResource(R.string.desc_profile_icon)
        )
        Spacer(modifier = Modifier.width(statusRowSpacerWidth))
        Text(text = "Hi, ${loginViewModel.displayName?.toUpperCase(Locale.current)}")
    }
}

@Composable
private fun BookRow(bookUi: BookUi) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = bookRowBottomPadding),
        shape = RectangleShape,
        backgroundColor = Color.White,
        elevation = bookRowElevation
    ) {


        ConstraintLayout(
            modifier = Modifier.padding(bookRowPadding)
        ) {

            val (image, titleText, authorText, startDateText, finishDateText, thumbsUpIcon) = createRefs()

            AsyncImage(
                model = bookUi.photoUrl,
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
                        end = if (bookUi.rating > 4) thumbsUpIcon.start else parent.end,
                        startMargin = bookColumnStartEndMargin,
                        endMargin = if (bookUi.rating > 4) bookColumnStartEndMargin else defaultEndMargin
                    )
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                },
                text = bookUi.title,
                overflow = TextOverflow.Ellipsis
            )

            if (bookUi.rating > 4) {
                Icon(
                    modifier = Modifier.constrainAs(thumbsUpIcon) {
                        end.linkTo(parent.end, bookColumnStartEndMargin)
                        top.linkTo(parent.top)
                    },
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = stringResource(R.string.desc_thumbsup_icon),
                    tint = Green400
                )
            }

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
                text = if (bookUi.authors.isNotBlank())
                    stringResource(R.string.author_name, bookUi.authors) else "",
                overflow = TextOverflow.Clip,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.caption
            )

            Text(
                modifier = Modifier.constrainAs(startDateText) {
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
                text = stringResource(
                    R.string.read_start_date,
                    bookUi.startedReading?.formatDate().toString()
                ),
                overflow = TextOverflow.Clip,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.caption
            )

            Text(
                modifier = Modifier.constrainAs(finishDateText) {
                    linkTo(
                        start = image.end,
                        end = parent.end,
                        top = startDateText.bottom,
                        bottom = parent.bottom,
                        startMargin = bookColumnStartEndMargin,
                        endMargin = bookColumnStartEndMargin
                    )
                    height = Dimension.wrapContent
                    width = Dimension.fillToConstraints
                },
                text = stringResource(
                    R.string.read_finish_date,
                    bookUi.finishedReading?.formatDate().toString()
                ),
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.caption
            )

        }

    }

}