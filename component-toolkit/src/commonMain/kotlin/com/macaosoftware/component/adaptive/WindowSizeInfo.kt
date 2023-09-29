package com.macaosoftware.component.adaptive

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed interface WindowSizeInfo {
    object ZeroSize : WindowSizeInfo
    object CompactPortrait : WindowSizeInfo
    object CompactLandscape : WindowSizeInfo
    object MediumPortrait : WindowSizeInfo
    object MediumLandscape : WindowSizeInfo
    object ExpandedPortrait : WindowSizeInfo
    object ExpandedLandscape : WindowSizeInfo

    companion object {
        fun fromWidthDp(widthDp: Dp, isPortrait: Boolean): WindowSizeInfo {
            return when (widthDp) {
                in (0.dp..<24.dp) -> ZeroSize
                in (24.dp..<600.dp) -> if (isPortrait) CompactPortrait else CompactLandscape
                in (600.dp..<900.dp) -> if (isPortrait) MediumPortrait else MediumLandscape
                else -> if (isPortrait) ExpandedPortrait else ExpandedLandscape
            }
        }
    }

}