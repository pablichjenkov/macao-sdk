package com.pablichj.templato.component.core.topbar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

interface TopBarStatePresenter {
    var topBarState: MutableState<TopBarState>
    val topBarStyle: TopBarStyle
    fun setIcon1(icon1: ImageVector, onIcon1Click: (() -> Unit)?)
    fun setIcon2(icon2: ImageVector, onIcon2Click: (() -> Unit)?)
    fun setTitle(title: String, onTitleClick: (() -> Unit)?)
    var onBackPressEvent: () -> Unit
}

class TopBarStatePresenterDefault(
    override val topBarStyle: TopBarStyle = TopBarStyle(),
) : TopBarStatePresenter {

    override var onBackPressEvent: () -> Unit = {}

    override var topBarState: MutableState<TopBarState> = mutableStateOf(TopBarState())

    override fun setIcon1(icon1: ImageVector, onIcon1Click: (() -> Unit)?) {
        val update = topBarState.value.copy(
            icon1 = icon1,
            onIcon1Click = onIcon1Click
        )
        topBarState.value = update
    }

    override fun setIcon2(icon2: ImageVector, onIcon2Click: (() -> Unit)?) {
        val update = topBarState.value.copy(
            icon2 = icon2,
            onIcon2Click = onIcon2Click
        )
        topBarState.value = update
    }

    override fun setTitle(title: String, onTitleClick: (() -> Unit)?) {
        val update = topBarState.value.copy(
            title = title,
            onTitleClick = onTitleClick
        )
        topBarState.value = update
    }

}
