package com.compose.jreader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.compose.jreader.ui.screens.login.ReaderLoginScreen
import com.compose.jreader.ui.screens.login.ReaderLoginViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.loginScreen(
    onNavigateToHomeScreen: () -> Unit
) {
    composable(ReaderScreens.LoginScreen.name) {
        val loginViewModel = hiltViewModel<ReaderLoginViewModel>()
        ReaderLoginScreen(loginViewModel, onNavigateToHomeScreen)
    }
}

internal fun NavController.navigateToLogin() {
    this.navigate(ReaderScreens.LoginScreen.name)
}
