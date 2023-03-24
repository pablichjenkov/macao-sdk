package com.pablichj.incubator.uistate3.node.navbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class NavBarStyle(
    var bgColor: Color = Color.Cyan
)

class NavBarItemStyle(
    var unselectedColor: Color = Color.LightGray,
    var selectedColor: Color = Color.Gray,
    var textSize: Dp = 14.dp
)
