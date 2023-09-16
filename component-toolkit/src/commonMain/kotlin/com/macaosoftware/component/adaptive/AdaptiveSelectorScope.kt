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
    private var currentMaxWidthPixel: Int = 0

    fun updateMaxWidth(density: Density, maxWidthPixel: Int) {
        val pixelDelta = abs(maxWidthPixel.minus(currentMaxWidthPixel))
        if (pixelDelta < PIXEL_DELTA_MIN) {
            return
        }
        maxWidthDp.value = with(density) {
            currentMaxWidthPixel = maxWidthPixel
            maxWidthPixel.toDp()
        }
    }

    override val windowSizeInfoState: State<WindowSizeInfo> = derivedStateOf {
        WindowSizeInfo.fromWidthDp(maxWidthDp.value)
    }

}
