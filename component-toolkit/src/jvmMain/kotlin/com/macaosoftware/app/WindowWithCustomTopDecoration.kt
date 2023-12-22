package com.macaosoftware.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope

@Composable
fun FrameWindowScope.WindowWithCustomTopDecoration(
    onMinimizeClick: () -> Unit,
    onMaximizeClick: () -> Unit,
    onCloseClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column {
        WindowDraggableArea {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.LightGray)
            ) {
                Row {
                    Spacer(modifier = Modifier.size(20.dp))
                    Box(
                        modifier = Modifier
                            .padding(top = 14.dp, end = 8.dp)
                            .pointerHoverIcon(icon = PointerIcon.Hand)
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                            .clickable { onCloseClick.invoke() }
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 14.dp, end = 8.dp)
                            .pointerHoverIcon(icon = PointerIcon.Hand)
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color.Yellow)
                            .clickable { onMinimizeClick.invoke() }
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .pointerHoverIcon(icon = PointerIcon.Hand)
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color.Green)
                            .clickable { onMaximizeClick.invoke() }
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                ) {
                    Box(
                        modifier = Modifier.clickable { onRefreshClick.invoke() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                    Box(
                        modifier = Modifier.clickable { onBackClick.invoke() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                }
            }
        }
        content()
    }
}
