package com.compose.jreader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun ReaderNavigation() {

    val navController = rememberAnimatedNavController()
    val animationTime = 1000

    AnimatedNavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(durationMillis = animationTime)
            )
        }
    ) {

        splashScreen(
            onNavigateToNextScreen = { isLoggedIn ->
                if (isLoggedIn) {
                    navController.navigateToHome()
                } else {
                    navController.navigateToLogin()
                }
            }
        )

        loginScreen(
            onNavigateToHomeScreen = {
                navController.navigateToHome()
            }
        )

        homeScreen(
            onNavigateToLoginScreen = {
                navController.navigateToLogin()
            },
            onNavigateToStatusScreen = {
                navController.navigateToStatus()
            },
            onNavigateToSearchScreen = {
                navController.navigateToSearch()
            },
            onNavigateToUpdateScreen = { id ->
                navController.navigateToUpdateScreen(id)
            }
        )

        searchScreen(
            onNavigateBack = {
                navController.popBackStack()
            }, onNavigateToDetails = { id ->
                navController.navigateToDetails(id)
            }
        )

        detailsScreen(
            onNavigateBack = {
                navController.popBackStack()
            }
        )

        updateScreen(
            onNavigateBack = {
                navController.popBackStack()
            }
        )

        statusScreen(
            onNavigateBack = {
                navController.popBackStack()
            }
        )

    }

}