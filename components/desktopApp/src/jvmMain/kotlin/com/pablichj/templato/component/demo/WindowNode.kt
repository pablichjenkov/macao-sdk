package com.pablichj.templato.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface WindowNode {
    @Composable
    fun WindowContent(modifier: Modifier)
}

sealed interface WindowNodeSample {
    object Drawer : WindowNodeSample
    object Panel : WindowNodeSample
    object Navbar : WindowNodeSample
    object FullApp : WindowNodeSample
}