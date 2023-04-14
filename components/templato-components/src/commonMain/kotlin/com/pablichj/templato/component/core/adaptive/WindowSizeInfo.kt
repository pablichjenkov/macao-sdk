package com.pablichj.templato.component.core.adaptive

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed interface WindowSizeInfo {
    object Compact : WindowSizeInfo
    object Medium : WindowSizeInfo
    object Expanded : WindowSizeInfo

    companion object {
        fun fromWidthDp(widthDp: Dp): WindowSizeInfo {
            return when (widthDp) {
                in (0.dp..600.dp) -> Compact
                in (600.dp..840.dp) -> Medium
                else -> Expanded
            }
        }
    }

}