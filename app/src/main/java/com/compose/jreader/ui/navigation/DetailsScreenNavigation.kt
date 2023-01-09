package com.compose.jreader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.compose.jreader.ui.screens.details.BookDetailsScreen
import com.compose.jreader.ui.screens.details.DetailsViewModel
import com.compose.jreader.utils.Constants
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.detailsScreen(
    onNavigateBack: () -> Unit
) {

    val route = "${ReaderScreens.DetailScreen.name}/{bookId}"
    composable(
        route = route,
        arguments = listOf(navArgument(Constants.ARG_BOOK_ID) {
            type = NavType.StringType

        })
    ) { navBackStackEntry ->
        val bookId = navBackStackEntry.arguments?.getString(Constants.ARG_BOOK_ID) ?: ""
        val viewModel = hiltViewModel<DetailsViewModel>()
        BookDetailsScreen(bookId, viewModel, onNavigateBack)
    }

}

internal fun NavController.navigateToDetails(bookId: String) {
    val route = "${ReaderScreens.DetailScreen.name}/$bookId"
    this.navigate(route)
}