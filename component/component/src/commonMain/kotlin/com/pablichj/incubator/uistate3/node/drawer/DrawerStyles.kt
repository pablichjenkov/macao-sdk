package com.pablichj.incubator.uistate3.node.drawer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class DrawerHeaderStyle(
    var bgColor: Color = Color.Cyan,
    var titleTextSize: Dp = 20.dp
)

class DrawerItemStyle(
    var unselectedColor: Color = Color.LightGray,
    var selectedColor: Color = Color.Gray,
    var textSize: Dp = 14.dp
)
