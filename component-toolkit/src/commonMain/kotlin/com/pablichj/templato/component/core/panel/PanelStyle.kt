package com.pablichj.templato.component.core.panel

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PanelStyle(
    val bgColor: Color = Color.LightGray,
    val headerHeight: Dp = 120.dp,
    val headerBgColor: Color = Color.Cyan,
    val horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    val titleTextSize: TextUnit = TextUnit(20.0F, TextUnitType.Sp),
    val descriptionTextSize: TextUnit = TextUnit(16.0F, TextUnitType.Sp),
    val unselectedColor: Color = Color.LightGray,
    val selectedColor: Color = Color.Gray,
    val borderColor: Color = Color.Black,
    val itemTextSize: TextUnit = TextUnit(16.0F, TextUnitType.Sp)
)
