package com.pablichj.templato.component.core.topbar

import androidx.compose.ui.graphics.vector.ImageVector

data class TopBarState(
    val title: String? = null,
    val onTitleClick: (() -> Unit)? = null,
    val icon1: ImageVector? = null,
    val onIcon1Click: (() -> Unit)? = null,
    val icon2: ImageVector? = null,
    val onIcon2Click: (() -> Unit)? = null
)