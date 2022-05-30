package com.compose.jreader.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.compose.jreader.ui.screens.details.BookDetailsScreen
import com.compose.jreader.ui.screens.home.ReaderHomeScreen
import com.compose.jreader.ui.screens.login.ReaderLoginScreen
import com.compose.jreader.ui.screens.search.ReaderBookSearchScreen
import com.compose.jreader.ui.screens.search.SearchViewModel
import com.compose.jreader.ui.screens.splash.ReaderSplashScreen
import com.compose.jreader.ui.screens.update.ReaderBookUpdateScreen
import com.compose.jreader.utils.Constants
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
            val viewModel: SearchViewModel = hiltViewModel()
            ReaderBookSearchScreen(navController, viewModel)
        }

        // Details screen navigation
        val route = "${ReaderScreens.DetailScreen.name}/{bookId}"
        composable(
            route = route,
            arguments = listOf(navArgument(Constants.ARG_BOOK_ID) {
                type = NavType.StringType

            })
        ) { navBackStackEntry ->
            val bookId = navBackStackEntry.arguments?.getString(Constants.ARG_BOOK_ID) ?: ""
            BookDetailsScreen(navController, bookId)
        }

        // Update screen navigation
        val updateScreenRoute = "${ReaderScreens.UpdateScreen.name}/{bookId}"
        composable(
            route = updateScreenRoute,
            arguments = listOf(navArgument(Constants.ARG_BOOK_ID) {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val bookId = navBackStackEntry.arguments?.getString(Constants.ARG_BOOK_ID) ?: ""
            ReaderBookUpdateScreen(navController, bookId)
        }

    }

}