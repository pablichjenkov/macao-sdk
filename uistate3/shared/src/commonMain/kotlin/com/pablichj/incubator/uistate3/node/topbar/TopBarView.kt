package com.pablichj.incubator.uistate3.node.topbar

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.FloatingBackButton
import com.pablichj.incubator.uistate3.node.Component

@Composable
fun TopBarRender(
    modifier: Modifier,
    topBarState: ITopBarState,
    childComponent: Component?
) {
    var startX by remember(topBarState) { mutableStateOf(Float.MAX_VALUE) }
    var deltaX by remember(topBarState) { mutableStateOf(0f) }
    var showBackNavigation = derivedStateOf { deltaX > 100 && startX < 30 }

    Scaffold(
        modifier = modifier,
        topBar = { TopBar(topBarState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(2.dp, Color.Green)
                .padding(paddingValues)
                .pointerInput(topBarState) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            println("Pablo::onDragStart offset = $offset")
                            startX = offset.x
                        },
                        onDrag = { inputChange, offset ->
                            println("Pablo::onDrag deltaX = $deltaX, offset = $offset")
                            inputChange.consume()
                            deltaX += offset.x
                            if (startX < 30 && deltaX > 250) {
                                startX = Float.MAX_VALUE
                                deltaX = 0f
                                topBarState.handleBackPress()
                            }
                        },
                        onDragCancel = {
                            startX = Float.MAX_VALUE
                            deltaX = 0f
                            println("Pablo::onDragCancel")
                        },
                        onDragEnd = {
                            startX = Float.MAX_VALUE
                            deltaX = 0f
                            println("Pablo::onDragEnd")
                        }
                    )
                }
        ) {
            if (childComponent != null) {
                childComponent.Content(Modifier)
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