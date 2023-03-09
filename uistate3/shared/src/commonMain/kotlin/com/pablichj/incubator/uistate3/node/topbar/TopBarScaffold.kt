package com.pablichj.incubator.uistate3.node.topbar

import TopBar
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.FloatingBackButton
import com.pablichj.incubator.uistate3.node.Component

private val PredictiveBackAreaWidth = 100
private val PredictiveBackDragWidth = 50

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopBarScaffold(
    modifier: Modifier,
    topBarState: ITopBarState,
    childComponent: Component?,
    prevChildComponent: Component?,
    animationType: AnimationType
) {
    var deltaX by remember(topBarState) { mutableStateOf(0f) }
    var deltaXMax by remember(topBarState) { mutableStateOf(0f) }
    var dragState by remember(topBarState) { mutableStateOf<DragState>(DragState.None) }

    Scaffold(
        modifier = modifier,
        topBar = { TopBar(topBarState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(topBarState) {
                    forEachGesture {
                        awaitPointerEventScope {
                            val eventDown = awaitFirstDown(requireUnconsumed = true)
                            println("TopBarScaffold::awaitFirstDown position: ${eventDown.position}, delta: ${eventDown.scrollDelta}")

                            if (eventDown.position.x < PredictiveBackAreaWidth) {
                                eventDown.consume()
                                val startX = eventDown.position.x

                                do {
                                    val event: PointerEvent = awaitPointerEvent(PointerEventPass.Main)
                                    // ACTION_MOVE loop
                                    event.changes.forEach {
                                        println("TopBarScaffold::awaitPointerEvent position: ${it.position}, delta: ${deltaX}, dragState=$dragState")
                                        // Consuming event prevents other gestures or scroll to intercept
                                        it.consume()
                                    }

                                    event.changes.lastOrNull()?.let {
                                        deltaX = it.position.x - startX
                                    }

                                    if (deltaX > PredictiveBackDragWidth) {
                                        dragState = DragState.PredictiveBack
                                        if (deltaXMax < deltaX) {
                                            deltaXMax = deltaX
                                        } else {
                                            // Cancel predictive back if the user reverse the move
                                            // by the same predictive back width amount
                                            if (deltaXMax > deltaX + deltaXMax*0.2) {
                                                dragState = DragState.None
                                                deltaX = 0.0f
                                                deltaXMax = 0.0f
                                                return@awaitPointerEventScope
                                            }
                                        }
                                    }

                                } while (event.changes.any { it.pressed })

                                println("TopBarScaffold::onDragEnd")
                                if (dragState == DragState.PredictiveBack) {
                                    topBarState.handleBackPress()
                                }

                                deltaX = 0.0f
                                deltaXMax = 0.0f
                                dragState = DragState.None
                            }
                        }
                    }
                }
        ) {
            if (childComponent != null) {
                /*AnimatedContent(
                    targetState = childComponent,
                    transitionSpec = { getTransitionByAnimationType(animationType) }
                ) {
                    it.Content(Modifier)
                }*/
                when (dragState) {
                    DragState.None -> {
                        AnimatedContent(
                            targetState = childComponent,
                            transitionSpec = { getTransitionByAnimationType(animationType) }
                        ) {
                            it.Content(Modifier)
                        }
                    }
                    DragState.PredictiveBack -> {
                        prevChildComponent?.Content(Modifier)

                        val xOffset = deltaX.toInt() - PredictiveBackDragWidth

                        Box(Modifier.offset {
                            IntOffset(xOffset,0)
                        }) {
                            childComponent.Content(Modifier)
                        }

                        // "Predictive back" Indicator
                        FloatingBackButton(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .offset { IntOffset(xOffset, 0) },
                            onClick = {}
                        )
                    }
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

sealed class AnimationType {
    object Direct : AnimationType()
    object Reverse : AnimationType()
    object Enter : AnimationType()
    object Exit : AnimationType()
}

sealed class DragState {
    object None : DragState()
    object PredictiveBack : DragState()
}