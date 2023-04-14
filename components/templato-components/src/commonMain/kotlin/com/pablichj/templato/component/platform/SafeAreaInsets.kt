package com.pablichj.templato.component.platform

import androidx.compose.runtime.staticCompositionLocalOf

class SafeAreaInsets {
    var start: Int = 0
    var top: Int = 0
    var end: Int = 0
    var bottom: Int = 0
}

val LocalSafeAreaInsets =
    staticCompositionLocalOf<SafeAreaInsets> { SafeAreaInsets() }
