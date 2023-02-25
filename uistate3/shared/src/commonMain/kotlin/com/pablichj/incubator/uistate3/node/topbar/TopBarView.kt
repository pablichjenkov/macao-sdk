package com.pablichj.incubator.uistate3.node.topbar

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.FloatingBackButton
import com.pablichj.incubator.uistate3.node.Component

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopBarRender(
    modifier: Modifier,
    topBarState: ITopBarState,
    childComponent: Component?,
    isPush: Boolean
) {
    var pointerEnabled by remember(topBarState) { mutableStateOf(true) }
    var startX by remember(topBarState) { mutableStateOf(Float.MAX_VALUE) }
    var deltaX by remember(topBarState) { mutableStateOf(0f) }
    var showBackNavigation = derivedStateOf { deltaX > 100 && startX <= 50 }

    Scaffold(
        modifier = modifier,
        topBar = { TopBar(topBarState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInputEnabler(pointerEnabled, topBarState) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            println("Pablo::onDragStart offset = $offset")
                            if (offset.x > 50) {
                                pointerEnabled = false
                            }
                            startX = offset.x
                        },
                        onDrag = { inputChange, offset ->
                            println("Pablo::onDrag deltaX = $deltaX, offset = $offset")
                            inputChange.consume()
                            deltaX += offset.x
                        },
                        onDragCancel = {
                            println("Pablo::onDragCancel")
                            startX = Float.MAX_VALUE
                            deltaX = 0f
                        },
                        onDragEnd = {
                            println("Pablo::onDragEnd")
                            if (startX <= 50 && deltaX > 250) {
                                startX = Float.MAX_VALUE
                                deltaX = 0f
                                topBarState.handleBackPress()
                            }
                            startX = Float.MAX_VALUE
                            deltaX = 0f
                        }
                    )
                }
        ) {
            if (childComponent != null) {
                AnimatedContent(
                    targetState = childComponent,
                    transitionSpec = {
                        slideInHorizontally(
                            initialOffsetX = { fullWidth ->
                                if (isPush) {
                                    fullWidth
                                } else {
                                    -fullWidth
                                }
                            },
                            animationSpec = tween(
                                durationMillis = 300,
                                delayMillis = 0
                            )
                        ) /*+ fadeIn(animationSpec = tween())*/ with
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth ->
                                        if (isPush) {
                                            -fullWidth
                                        } else {
                                            fullWidth
                                        }
                                    },
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        delayMillis = 0
                                    )
                                ) /*+ fadeOut(animationSpec = tween())*/
                    }
                ) {
                    it.Content(Modifier)
                }

                // "Predictive back" bellow
                if (showBackNavigation.value) {
                    FloatingBackButton(
                        modifier = Modifier.align(Alignment.CenterStart),
                        onClick = {}
                    )
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

fun Modifier.pointerInputEnabler(
    enable: Boolean,
    key1: Any?,
    block: suspend PointerInputScope.() -> Unit
): Modifier {
    return if (enable) {
        this.pointerInput(key1, block)
    } else {
        Modifier
    }
}

@Composable
fun TopBar(
    topBarState: ITopBarState
) {

    val topBarIcon1: ImageVector? by remember(key1 = topBarState) {
        topBarState.icon1
    }

    val topBarIcon2: ImageVector? by remember(key1 = topBarState) {
        topBarState.icon2
    }

    val topBarTitle: String? by remember(key1 = topBarState) {
        topBarState.title
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(2.dp, Color.Blue)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        topBarIcon1?.let {
            Icon(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        topBarState.onIcon1Click()
                    },
                imageVector = it,
                contentDescription = "TopBar icon"
            )
        }

        topBarIcon2?.let {
            Icon(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        topBarState.onIcon2Click()
                    },
                imageVector = it,
                contentDescription = "TopBar icon"
            )
        }

        topBarTitle?.let {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .clickable {
                        topBarState.onTitleClick()
                    },
                text = it,
            )
        }

    }
}