package com.pablichj.incubator.uistate3.node.drawer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

class DrawerHeaderStyle(
    var bgColor: Color = Color.Cyan,
    var titleTextSize: TextUnit = 20.sp,
    var descriptionTextSize: TextUnit = 16.sp
)

class DrawerItemStyle(
    var unselectedColor: Color = Color.LightGray,
    var selectedColor: Color = Color.Gray,
    var textSize: TextUnit = 14.sp
)
