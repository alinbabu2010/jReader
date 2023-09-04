package com.compose.jreader.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun ReaderNavigation() {

    val navController = rememberNavController()
    val animationTime = 1000

    NavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(durationMillis = animationTime)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(durationMillis = animationTime)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(durationMillis = animationTime)
            )
        }
    ) {

        splashScreen(
            onNavigateToNextScreen = { isLoggedIn ->
                if (isLoggedIn) {
                    navController.navigateToHome(true)
                } else {
                    navController.navigateToLogin(true)
                }
            }
        )

        loginScreen(
            onNavigateToHomeScreen = {
                navController.navigateToHome(false)
            }
        )

        homeScreen(
            onNavigateToLoginScreen = {
                navController.navigateToLogin(false)
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