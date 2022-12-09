package com.pablichj.encubator.node

import android.app.Activity
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.window.layout.WindowMetricsCalculator
import com.pablichj.encubator.node.nodes.IWindowSizeInfoProvider
import com.pablichj.encubator.node.nodes.WindowSizeInfo

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class AndroidWindowSizeInfoProvider(
    val activity: Activity,
) : IWindowSizeInfoProvider {

    @Composable
    override fun windowSizeInfo(): State<WindowSizeInfo> {
        val windowSize = calculateWindowSizeClass(activity)

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