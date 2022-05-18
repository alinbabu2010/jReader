package com.compose.jreader.ui.navigation

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    HomeScreen,
    SearchScreen,
    DetailScreen,
    UpdateScreen,
    StatusScreen;

    companion object {
        fun fromRoute(route: String?): ReaderScreens =
            when (route?.substringBefore("/")) {
                SplashScreen.name -> SplashScreen
                LoginScreen.name -> LoginScreen
                CreateAccountScreen.name -> CreateAccountScreen
                HomeScreen.name -> HomeScreen
                SearchScreen.name -> SearchScreen
                DetailScreen.name -> DetailScreen
                UpdateScreen.name -> UpdateScreen
                StatusScreen.name -> StatusScreen
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route $route is not recognised")
            }
    }

}