package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.ui.theme.NavHostAnimationIssueTheme
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data object LoadingRoute

@Serializable
data class DefaultRoute(
    val color: Int,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavHostAnimationIssueTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            text = {
                                Text(text = "Next")
                            },
                            icon = {},
                            onClick = {
                                navController.navigate(
                                    route = DefaultRoute(
                                        color = Color(
                                            red = Random.nextFloat(),
                                            green = Random.nextFloat(),
                                            blue = Random.nextFloat(),
                                        ).toArgb()
                                    )
                                )
                            },
                        )
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = LoadingRoute,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        enterTransition = { fadeIn() },
                        exitTransition = { fadeOut() },
                        popEnterTransition = { fadeIn() },
                        popExitTransition = { fadeOut() },
                    ) {
                        composable<LoadingRoute> {
                            Box(modifier = Modifier.fillMaxSize())
                        }
                        composable<DefaultRoute>(
                            enterTransition = { ENTER_TRANSITION },
                            exitTransition = { EXIT_TRANSITION },
                        ) {
                            val route = it.toRoute<DefaultRoute>()
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(route.color)),
                            )
                        }
                    }
                }
            }
        }
    }
}

private const val ANIMATION_DURATION = 700

private val ENTER_TRANSITION = slideInHorizontally(
    animationSpec = tween(
        durationMillis = ANIMATION_DURATION,
    ),
    initialOffsetX = { -it },
)

private val EXIT_TRANSITION = slideOutHorizontally(
    animationSpec = tween(durationMillis = ANIMATION_DURATION),
    targetOffsetX = { it },
)
