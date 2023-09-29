package com.macaosoftware.component.adaptive

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kotlin.math.abs

private const val PIXEL_DELTA_MIN = 100

internal interface AdaptiveSelectorScope {
    val windowSizeInfoState: State<WindowSizeInfo>
}

internal class AdaptiveSelectorScopeImpl : AdaptiveSelectorScope {

    private var maxWidthDp = mutableStateOf(0.dp)
    private var maxHeightDp = mutableStateOf(0.dp)
    private var currentMaxWidthPixel: Int = 0
    private var currentMaxHeightPixel: Int = 0

    fun updateMaxDimensions(density: Density, maxWidthPixel: Int, maxHeightPixel: Int) {
        calculateWindowSizeInfoWithWidth(density, maxWidthPixel)
        calculateWindowSizeInfoWithHeight(density, maxHeightPixel)
    }

    private fun calculateWindowSizeInfoWithWidth(density: Density, maxWidthPixel: Int) {
        val pixelDelta = abs(maxWidthPixel.minus(currentMaxWidthPixel))
        if (pixelDelta < PIXEL_DELTA_MIN) {
            return
        }
        currentMaxWidthPixel = maxWidthPixel

        maxWidthDp.value = with(density) {
            maxWidthPixel.toDp()
        }
    }

    private fun calculateWindowSizeInfoWithHeight(density: Density, maxHeightPixel: Int) {
        val pixelDelta = abs(maxHeightPixel.minus(currentMaxHeightPixel))
        if (pixelDelta < PIXEL_DELTA_MIN) {
            return
        }
        currentMaxHeightPixel = maxHeightPixel

        maxHeightDp.value = with(density) {
            maxHeightPixel.toDp()
        }
    }

    override val windowSizeInfoState: State<WindowSizeInfo> = derivedStateOf {
        maxHeightDp.value
        val isPortrait = currentMaxWidthPixel <= currentMaxHeightPixel
        WindowSizeInfo.fromWidthDp(maxWidthDp.value, isPortrait)
    }

}
