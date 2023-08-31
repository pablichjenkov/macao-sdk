package com.pablichj.templato.component.core.topbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TopBarStyle(
    val barHeight: Dp = 48.dp,
    val barColor: Color = Color.LightGray,
    val borderColor: Color = Color.Black,
    val textSize: TextUnit = 16.sp
)

data class TopBarItem(
    val label: String,
    val icon: ImageVector,
)

