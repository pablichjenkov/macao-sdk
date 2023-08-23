package com.pablichj.templato.component.core.stack

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.graphics.vector.ImageVector

data class StackBarItem(
    val label: String,
    val icon: ImageVector,
)

internal val EmptyStackBarItem = StackBarItem(
    "Empty Title",
    Icons.Default.Close,
)