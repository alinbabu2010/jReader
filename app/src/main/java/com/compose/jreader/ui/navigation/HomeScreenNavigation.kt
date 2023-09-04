package com.compose.jreader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.compose.jreader.ui.screens.home.ReaderHomeScreen
import androidx.navigation.compose.composable

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.homeScreen(
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToStatusScreen: () -> Unit,
    onNavigateToUpdateScreen: (String) -> Unit
) {

    composable(ReaderScreens.HomeScreen.name) {
        ReaderHomeScreen(
            onNavigateToLoginScreen = onNavigateToLoginScreen,
            onNavigateToSearchScreen = onNavigateToSearchScreen,
            onNavigateToStatusScreen = onNavigateToStatusScreen,
            onNavigateToUpdateScreen = onNavigateToUpdateScreen
        )
    }
}

internal fun NavController.navigateToHome(isFromSplash: Boolean) {
    navigate(ReaderScreens.HomeScreen.name) {
        popUpTo(
            if (isFromSplash) ReaderScreens.SplashScreen.name
            else ReaderScreens.LoginScreen.name
        ) {
            inclusive = true
        }
    }
}