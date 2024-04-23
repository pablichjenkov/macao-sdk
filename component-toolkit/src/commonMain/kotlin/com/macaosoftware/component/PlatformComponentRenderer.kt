package com.macaosoftware.component

import androidx.compose.runtime.Composable
import com.macaosoftware.component.core.Component

@Composable
expect fun PlatformComponentRenderer(rootComponent: Component)
