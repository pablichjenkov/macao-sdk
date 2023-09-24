package com.macaosoftware.component.adaptive

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed interface WindowSizeInfo {
    object ZeroSize : WindowSizeInfo
    object Compact : WindowSizeInfo
    object Medium : WindowSizeInfo
    object Expanded : WindowSizeInfo

    companion object {
        fun fromWidthDp(widthDp: Dp): WindowSizeInfo {
            return when (widthDp) {
                in (0.dp..<24.dp) -> ZeroSize
                in (24.dp..<600.dp) -> Compact
                in (600.dp..<840.dp) -> Medium
                else -> Expanded
            }
        }
    }

}