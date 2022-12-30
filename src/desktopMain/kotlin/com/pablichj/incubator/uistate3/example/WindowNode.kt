package com.pablichj.incubator.uistate3.example

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface WindowNode {
    @Composable
    fun Content(modifier: Modifier)
}

sealed interface WindowNodeSample {
    object Drawer : WindowNodeSample
    object Panel : WindowNodeSample
    object Navbar : WindowNodeSample
    object FullApp : WindowNodeSample
}