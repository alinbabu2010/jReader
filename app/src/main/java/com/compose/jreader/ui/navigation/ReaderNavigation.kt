package com.compose.jreader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import com.compose.jreader.screens.details.BookDetailsScreen
import com.compose.jreader.screens.home.ReaderHomeScreen
import com.compose.jreader.screens.login.ReaderLoginScreen
import com.compose.jreader.screens.search.ReaderBookSearchScreen
import com.compose.jreader.screens.splash.ReaderSplashScreen
import com.compose.jreader.screens.update.ReaderBookUpdateScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

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
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(durationMillis = animationTime)
            )
        }
    ) {


        // Splash screen navigation
        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController)
        }

        // Login screen navigation
        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController)
        }

        // Home screen navigation
        composable(ReaderScreens.HomeScreen.name) {
            ReaderHomeScreen(navController)
        }

        // Search screen navigation
        composable(ReaderScreens.SearchScreen.name) {
            ReaderBookSearchScreen(navController)
        }

        // Details screen navigation
        composable(ReaderScreens.DetailScreen.name) {
            BookDetailsScreen(navController)
        }

        // Update screen navigation
        composable(ReaderScreens.UpdateScreen.name) {
            ReaderBookUpdateScreen(navController)
        }

    }

}