package com.pablichj.incubator.uistate3.node.topbar

import TopBar
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.incubator.uistate3.node.Component

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopBarSystemPredictiveBack(
    modifier: Modifier,
    topBarState: ITopBarState,
    childComponent: Component?,
    animationType: AnimationType
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(topBarState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (childComponent != null) {
                AnimatedContent(
                    targetState = childComponent,
                    transitionSpec = { getTransitionByAnimationType(animationType) }
                ) {
                    it.Content(Modifier)
                }
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = "Empty Stack, Please add some children",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun getTransitionByAnimationType(animationType: AnimationType): ContentTransform {
    return when (animationType) {
        AnimationType.Direct -> {
            slideInHorizontally(
                initialOffsetX = { fullWidth ->
                    fullWidth
                },
                animationSpec = tween(
                    durationMillis = 300,
                    delayMillis = 0
                )
            ) with//+ fadeIn(animationSpec = tween())
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth ->
                            -fullWidth
                        },
                        animationSpec = tween(
                            durationMillis = 300,
                            delayMillis = 0
                        )
                    ) //+ fadeOut(animationSpec = tween())
        }
        AnimationType.Reverse -> {
            slideInHorizontally(
                initialOffsetX = { fullWidth ->
                    -fullWidth
                },
                animationSpec = tween()
            ) with//+ fadeIn(animationSpec = tween())
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth ->
                            fullWidth
                        },
                        animationSpec = tween()
                    ) //+ fadeOut(animationSpec = tween())
        }
        AnimationType.Exit,
        AnimationType.Enter -> {
            fadeIn(
                animationSpec = tween()
            ) with fadeOut(
                animationSpec = tween()
            )
        }
    }
}