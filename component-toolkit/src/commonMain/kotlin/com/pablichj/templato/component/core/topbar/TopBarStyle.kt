package com.pablichj.templato.component.core.topbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class TopBarStyle(
    val stackBarPosition: StackBarPosition = StackBarPosition.Top,
    var barColor: Color = Color.DarkGray,
    var textSize: Dp = 14.dp
)

enum class StackBarPosition {
    Top,
    Bottom
}
