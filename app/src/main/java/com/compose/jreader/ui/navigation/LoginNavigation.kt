package com.compose.jreader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.compose.jreader.ui.screens.login.ReaderLoginScreen
import com.compose.jreader.ui.screens.login.ReaderLoginViewModel
import androidx.navigation.compose.composable

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.loginScreen(
    onNavigateToHomeScreen: () -> Unit
) {
    composable(ReaderScreens.LoginScreen.name) {
        val loginViewModel = hiltViewModel<ReaderLoginViewModel>()
        ReaderLoginScreen(loginViewModel, onNavigateToHomeScreen)
    }
}

internal fun NavController.navigateToLogin(isFromSplash: Boolean) {
    navigate(ReaderScreens.LoginScreen.name) {
        popUpTo(
            if (isFromSplash) ReaderScreens.SplashScreen.name
            else ReaderScreens.HomeScreen.name
        ) {
            inclusive = true
        }
    }
}
