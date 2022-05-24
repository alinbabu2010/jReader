package com.compose.jreader.ui.screens.search

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.compose.jreader.ui.components.ReaderAppBar

@Preview
@Composable
fun ReaderBookSearchScreen(navController: NavHostController = NavHostController(LocalContext.current)) {

    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search Books",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false
        ) {
            navController.popBackStack()
        }

    }) {


    }

}