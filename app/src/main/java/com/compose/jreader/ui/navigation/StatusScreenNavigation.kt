package com.compose.jreader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.compose.jreader.ui.screens.home.HomeViewModel
import com.compose.jreader.ui.screens.login.ReaderLoginViewModel
import com.compose.jreader.ui.screens.status.StatusScreen
import androidx.navigation.compose.composable

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.statusScreen(
    onNavigateBack: () -> Unit
) {

    composable(ReaderScreens.StatusScreen.name) {
        val homeViewModel = hiltViewModel<HomeViewModel>()
        val loginViewModel = hiltViewModel<ReaderLoginViewModel>()
        StatusScreen(homeViewModel, loginViewModel, onNavigateBack)
    }

}

internal fun NavController.navigateToStatus() {
    this.navigate(ReaderScreens.StatusScreen.name)
}