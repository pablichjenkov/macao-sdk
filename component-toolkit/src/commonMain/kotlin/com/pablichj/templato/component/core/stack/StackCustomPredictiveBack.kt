package com.pablichj.templato.component.core.stack

import androidx.compose.animation.*
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import com.pablichj.templato.component.FloatingBackButton
import com.pablichj.templato.component.core.Component
import kotlinx.coroutines.launch

private val PredictiveBackAreaWidth = 100
private val PredictiveBackDragWidth = 50

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StackCustomPredictiveBack(
    modifier: Modifier,
    childComponent: Component?,
    prevChildComponent: Component?,
    animationType: AnimationType,
    onComponentSwipedOut: () -> Unit
) {
    var deltaX by remember(childComponent) { mutableStateOf(0f) }
    var deltaXMax by remember(childComponent) { mutableStateOf(0f) }
    var dragState by remember(childComponent) { mutableStateOf<DragState>(DragState.None) }
    var wasCancelled by remember(childComponent) { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(childComponent) {
                if (prevChildComponent == null) return@pointerInput
                awaitEachGesture {
                    val eventDown = awaitFirstDown(requireUnconsumed = true)
                    println("TopBarScaffold::awaitFirstDown position: ${eventDown.position}, delta: ${eventDown.scrollDelta}")

                    if (eventDown.position.x < PredictiveBackAreaWidth) {
                        eventDown.consume()
                        val startX = eventDown.position.x
                        wasCancelled = false

                        do {
                            val event: PointerEvent =
                                awaitPointerEvent(PointerEventPass.Main)
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
                                dragState = DragState.PredictiveBackLeft
                                if (deltaXMax < deltaX) {
                                    deltaXMax = deltaX
                                } else {
                                    // Cancel predictive back if the user reverse the move
                                    // by a fifth(1/5) of the maximum amount dragged.
                                    if (deltaXMax > deltaX + deltaXMax * 0.2) {
                                        wasCancelled = true
                                        break
                                    }
                                }
                            }

                        } while (event.changes.any { it.pressed })

                        coroutineScope.launch {

                            val targetValue = if (wasCancelled) {
                                PredictiveBackDragWidth
                            } else {
                                size.width + PredictiveBackDragWidth
                            }

                            animate(
                                initialValue = deltaX,
                                targetValue = targetValue.toFloat(),
                            ) { value, /* velocity */ _ ->
                                // Update alpha mutable state with the current animation value
                                deltaX = value
                            }
                            println("TopBarScaffold::onDragEnd wasCancelled = $wasCancelled")
                            if (!wasCancelled) {
                                onComponentSwipedOut()
                            }

                            deltaX = 0.0f
                            deltaXMax = 0.0f
                            dragState = DragState.None
                        }

                    } else if (
                        eventDown.position.x.toInt() > (size.width - PredictiveBackAreaWidth)
                    ) {
                        eventDown.consume()
                        val startX = eventDown.position.x
                        wasCancelled = false

                        do {
                            val event: PointerEvent =
                                awaitPointerEvent(PointerEventPass.Main)
                            // ACTION_MOVE loop
                            event.changes.forEach {
                                println("TopBarScaffold::awaitPointerEvent position: ${it.position}, delta: ${deltaX}, dragState=$dragState")
                                // Consuming event prevents other gestures or scroll to intercept
                                it.consume()
                            }

                            event.changes.lastOrNull()?.let {
                                deltaX = startX - it.position.x
                            }

                            if (deltaX > PredictiveBackDragWidth) {
                                dragState = DragState.PredictiveBackRight
                                if (deltaXMax < deltaX) {
                                    deltaXMax = deltaX
                                } else {
                                    // Cancel predictive back if the user reverse the move
                                    // by a fifth(1/5) of the maximum amount dragged.
                                    if (deltaXMax > deltaX + deltaXMax * 0.2) {
                                        wasCancelled = true
                                        break
                                    }
                                }
                            }

                        } while (event.changes.any { it.pressed })

                        coroutineScope.launch {

                            val targetValue = if (wasCancelled) {
                                PredictiveBackDragWidth
                            } else {
                                size.width + PredictiveBackDragWidth
                            }

                            animate(
                                initialValue = deltaX,
                                targetValue = targetValue.toFloat(),
                            ) { value, /* velocity */ _ ->
                                // Update alpha mutable state with the current animation value
                                deltaX = value
                            }
                            println("TopBarScaffold::onDragEnd wasCancelled = $wasCancelled")
                            if (!wasCancelled) {
                                onComponentSwipedOut()
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
            when (dragState) {
                DragState.None -> {
                    AnimatedContent(
                        targetState = childComponent,
                        transitionSpec = { getTransitionByAnimationType(animationType) }
                    ) {
                        it.Content(Modifier)
                    }
                }

                DragState.PredictiveBackLeft -> {
                    prevChildComponent?.Content(Modifier)

                    //TODO: Do not calculate this here because it triggers recomposition
                    // Computed in the producer coroutine end.
                    // Also, create a separate offset field for the FloatingBackButton, to
                    // animate it with different values and not trigger recomposition because
                    // it will be inside the lambda.
                    val xOffset = deltaX.toInt() - PredictiveBackDragWidth

                    Box(Modifier.offset {
                        println("Dragged Component xOffset = $xOffset")
                        IntOffset(xOffset, 0)
                    }) {
                        childComponent.Content(Modifier)
                    }

                    // "Predictive back" Indicator
                    FloatingBackButton(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .offset { IntOffset(xOffset / 2, 0) },
                        onClick = {}
                    )
                }

                DragState.PredictiveBackRight -> {
                    prevChildComponent?.Content(Modifier)

                    val xOffset = -deltaX.toInt() + PredictiveBackDragWidth

                    Box(Modifier.offset {
                        IntOffset(xOffset, 0)
                    }) {
                        childComponent.Content(Modifier)
                    }

                    // "Predictive back" Indicator
                    FloatingBackButton(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .offset { IntOffset(xOffset / 2, 0) },
                        onClick = {}
                    )

                }
            }
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                text = "StackCustomPredictiveBack Empty Stack, Please add some children",
                textAlign = TextAlign.Center
            )
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

private sealed class DragState {
    object None : DragState()
    object PredictiveBackLeft : DragState()
    object PredictiveBackRight : DragState()
}
