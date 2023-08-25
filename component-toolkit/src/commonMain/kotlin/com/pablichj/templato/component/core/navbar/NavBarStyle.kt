package com.pablichj.templato.component.core.navbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

data class NavBarStyle(
    val barSize: Dp = 56.dp,
    val showLabel: Boolean = true,
    val bgColor: Color = Color.Cyan,
    val unselectedColor: Color = Color.LightGray,
    val selectedColor: Color = Color.Gray,
    val textSize: Dp = 14.dp
)

data class NavBarNavItem(
    val label: String,
    val icon: ImageVector,
    var selected: Boolean
)