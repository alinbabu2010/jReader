package com.compose.jreader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.compose.jreader.ui.screens.update.ReaderBookUpdateScreen
import com.compose.jreader.ui.screens.update.UpdateViewModel
import com.compose.jreader.utils.Constants
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
internal fun NavGraphBuilder.updateScreen(
    onNavigateBack: () -> Unit
) {

    val updateScreenRoute = "${ReaderScreens.UpdateScreen.name}/{bookId}"
    composable(
        route = updateScreenRoute,
        arguments = listOf(navArgument(Constants.ARG_BOOK_ID) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val bookId = navBackStackEntry.arguments?.getString(Constants.ARG_BOOK_ID) ?: ""
        val viewModel = hiltViewModel<UpdateViewModel>()
        ReaderBookUpdateScreen(bookId, viewModel, onNavigateBack)
    }

}

internal fun NavController.navigateToUpdateScreen(bookId: String) {
    val route = "${ReaderScreens.UpdateScreen.name}/$bookId"
    this.navigate(route)
}