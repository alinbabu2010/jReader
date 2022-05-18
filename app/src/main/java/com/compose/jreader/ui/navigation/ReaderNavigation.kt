package com.compose.jreader.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.jreader.screens.details.BookDetailsScreen
import com.compose.jreader.screens.home.ReaderHomeScreen
import com.compose.jreader.screens.login.ReaderLoginScreen
import com.compose.jreader.screens.search.ReaderBookSearchScreen
import com.compose.jreader.screens.splash.ReaderSplashScreen
import com.compose.jreader.screens.update.ReaderBookUpdateScreen

@Composable
fun ReaderNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = ReaderScreens.SplashScreen.name) {


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