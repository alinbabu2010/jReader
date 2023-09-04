package com.compose.jreader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.compose.jreader.ui.screens.search.ReaderBookSearchScreen
import com.compose.jreader.ui.screens.search.SearchViewModel
import androidx.navigation.compose.composable

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.searchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDetails: (String) -> Unit
) {

    composable(ReaderScreens.SearchScreen.name) {
        val viewModel: SearchViewModel = hiltViewModel()
        ReaderBookSearchScreen(viewModel, onNavigateBack, onNavigateToDetails)
    }

}

internal fun NavController.navigateToSearch() {
    this.navigate(ReaderScreens.SearchScreen.name)
}