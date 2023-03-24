package com.pablichj.incubator.uistate3.node.panel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class PanelStyle(
    var bgColor: Color = Color.Cyan
)

class PanelItemStyle(
    var unselectedColor: Color = Color.LightGray,
    var selectedColor: Color = Color.Gray,
    var textSize: Dp = 14.dp
)
