package com.pablichj.templato.component.core.panel

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PanelStyle(
    val bgColor: Color = Color.LightGray,
    val headerHeight: Dp = 120.dp,
    val headerBgColor: Color = Color.Cyan,
    val horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    val titleTextSize: TextUnit = 20.sp,
    val descriptionTextSize: TextUnit = 16.sp,
    val unselectedColor: Color = Color.LightGray,
    val selectedColor: Color = Color.Gray,
    val borderColor: Color = Color.Black,
    val itemTextSize: TextUnit = 16.sp
)

data class PanelNavItem(
    val label: String,
    val icon: ImageVector,
    var selected: Boolean
)
