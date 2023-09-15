package com.macaosoftware.component.adaptive

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal interface AdaptiveSelectorScope {
    var maxWidthDp: MutableState<Dp>
    val windowSizeInfoState: State<WindowSizeInfo>
}

internal class AdaptiveSelectorScopeImpl(
    override var maxWidthDp: MutableState<Dp> = mutableStateOf(0.dp)
) : AdaptiveSelectorScope {

    override val windowSizeInfoState: State<WindowSizeInfo> = derivedStateOf {
        WindowSizeInfo.fromWidthDp(maxWidthDp.value)
    }

}
