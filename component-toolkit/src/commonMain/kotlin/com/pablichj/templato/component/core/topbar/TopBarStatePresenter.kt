package com.pablichj.templato.component.core.topbar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

data class TopBarState(
    val title: String? = null,
    val onTitleClick: (() -> Unit)? = null,
    val icon1: ImageVector? = null,
    val onIcon1Click: (() -> Unit)? = null,
    val icon2: ImageVector? = null,
    val onIcon2Click: (() -> Unit)? = null
)

interface TopBarStatePresenter {
    var topBarState: MutableState<TopBarState>
    fun setIcon1(icon1: ImageVector, onIcon1Click: (() -> Unit)?)
    fun setIcon2(icon2: ImageVector, onIcon2Click: (() -> Unit)?)
    fun setTitle(title: String, onTitleClick: (() -> Unit)?)
    fun handleBackPress()
}

class DefaultTopBarStatePresenter(
    val onHandleBackPress: (() -> Unit)? = null
) : TopBarStatePresenter {
    override var topBarState: MutableState<TopBarState> = mutableStateOf(TopBarState())

    override fun setIcon1(icon1: ImageVector, onIcon1Click: (() -> Unit)?) {
        val update  = topBarState.value.copy(
            icon1 = icon1,
            onIcon1Click = onIcon1Click
        )
        topBarState.value = update
    }

    override fun setIcon2(icon2: ImageVector, onIcon2Click: (() -> Unit)?) {
        val update  = topBarState.value.copy(
            icon2 = icon2,
            onIcon2Click = onIcon2Click
        )
        topBarState.value = update
    }

    override fun setTitle(title: String, onTitleClick: (() -> Unit)?) {
        val update  = topBarState.value.copy(
            title = title,
            onTitleClick = onTitleClick
        )
        topBarState.value = update
    }

    override fun handleBackPress() {
        onHandleBackPress?.invoke()
    }

}
