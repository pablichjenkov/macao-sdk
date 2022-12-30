package com.pablichj.incubator.uistate3.node.adaptable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

interface IWindowSizeInfoProvider {
    @Composable
    fun windowSizeInfo(): State<WindowSizeInfo>
}

sealed interface WindowSizeInfo {
    object Compact : WindowSizeInfo
    object Medium : WindowSizeInfo
    object Expanded : WindowSizeInfo
}