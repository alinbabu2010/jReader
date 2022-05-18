package com.compose.jreader.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.compose.jreader.R
import com.compose.jreader.utils.*
import kotlinx.coroutines.delay

@Preview
@Composable
fun ReaderSplashScreen(navController: NavHostController = NavHostController(LocalContext.current)) {

    val scale = remember {
        Animatable(0F)
    }

    LaunchedEffect(true) {
        scale.animateTo(
            targetValue = 0.9F,
            animationSpec = tween(800, easing = {
                OvershootInterpolator(4F).getInterpolation(it)
            })
        )
        delay(1500L)
    }

    Surface(
        modifier = Modifier
            .padding(splashSurfacePadding)
            .size(splashSurfaceSize)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = splashBorderWidth, color = Color.LightGray)
    ) {

        Column(
            modifier = Modifier.padding(splashColumnPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.h3,
                color = Color.Red.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(surfaceSpacerHeight))
            Text(
                text = stringResource(R.string.app_tagline),
                style = MaterialTheme.typography.h6,
                color = Color.Gray
            )

        }

    }

}