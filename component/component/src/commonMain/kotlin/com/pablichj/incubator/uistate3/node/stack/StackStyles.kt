package com.pablichj.incubator.uistate3.node.stack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class StackBarPosition {
    Top,
    Bottom
}

class StackStyle(
    val stackBarPosition: StackBarPosition = StackBarPosition.Top
)

class StackBarItemStyle(
    var barColor: Color = Color.Gray,
    var textSize: Dp = 14.dp
)
