package com.pablichj.templato.component.core.deeplink

import androidx.compose.runtime.staticCompositionLocalOf
import com.pablichj.templato.component.core.Component

val LocalRootComponentProvider =
    staticCompositionLocalOf<Component?> { null }
