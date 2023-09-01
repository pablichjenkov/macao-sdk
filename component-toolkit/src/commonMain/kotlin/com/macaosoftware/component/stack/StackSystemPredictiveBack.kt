package com.macaosoftware.component.stack

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.macaosoftware.component.core.Component

@Composable
fun StackSystemPredictiveBack(
    modifier: Modifier,
    childComponent: Component?,
    animationType: AnimationType
) {
    Box(
        modifier = modifier
            .fillMaxSize()
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
                text = "StackSystemPredictiveBack Empty Stack, Please add some children",
                textAlign = TextAlign.Center
            )
        }
    }
}

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
            ) togetherWith//+ fadeIn(animationSpec = tween())
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
            ) togetherWith//+ fadeIn(animationSpec = tween())
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
            ) togetherWith fadeOut(
                animationSpec = tween()
            )
        }
    }
}
