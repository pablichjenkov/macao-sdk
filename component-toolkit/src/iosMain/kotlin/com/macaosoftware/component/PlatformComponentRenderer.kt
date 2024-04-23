package com.macaosoftware.component

import androidx.compose.runtime.Composable
import com.macaosoftware.component.core.Component

@Composable
actual fun PlatformComponentRenderer(rootComponent: Component) = IosComponentRender(rootComponent)
