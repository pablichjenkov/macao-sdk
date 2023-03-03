package com.pablichj.incubator.uistate3.node

import android.app.Activity
import android.util.Log
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import com.pablichj.incubator.uistate3.node.adaptable.IWindowSizeInfoProvider
import com.pablichj.incubator.uistate3.node.adaptable.WindowSizeInfo

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class AndroidWindowSizeInfoProvider(
    val activity: Activity,
) : IWindowSizeInfoProvider() {

    @Composable
    override fun windowSizeInfo(): State<WindowSizeInfo> {
        val windowSize = calculateWindowSizeClass(activity)
        Log.d("AndroidWindowSizeInfoProvider", "windowSizeInfo: windowSize = $windowSize")
        return remember(windowSize) {
            derivedStateOf {
                when (windowSize.widthSizeClass) {
                    WindowWidthSizeClass.Compact -> WindowSizeInfo.Compact
                    WindowWidthSizeClass.Medium -> WindowSizeInfo.Medium
                    WindowWidthSizeClass.Expanded -> WindowSizeInfo.Expanded
                    else -> throw IllegalStateException()
                }
            }
        }
    }

}