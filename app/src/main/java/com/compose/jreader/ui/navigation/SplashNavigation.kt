package com.compose.jreader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.compose.jreader.ui.screens.login.ReaderLoginViewModel
import com.compose.jreader.ui.screens.splash.ReaderSplashScreen
import androidx.navigation.compose.composable

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.splashScreen(
    onNavigateToNextScreen: (Boolean) -> Unit
) {
    composable(ReaderScreens.SplashScreen.name) {
        val viewModel: ReaderLoginViewModel = hiltViewModel()
        ReaderSplashScreen { onNavigateToNextScreen(viewModel.isLoggedIn()) }
    }
}