package com.pablichj.templato.component.core.topbar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import com.pablichj.templato.component.core.drawer.DrawerNavigationProvider

interface TopBarStatePresenter {
    var topBarState: MutableState<TopBarState>
    val topBarStyle: TopBarStyle
    var onBackPressEvent: () -> Unit
}

class TopBarStatePresenterDefault(
    override val topBarStyle: TopBarStyle = TopBarStyle(),
) : TopBarStatePresenter {

    override var onBackPressEvent: () -> Unit = {}

    override var topBarState: MutableState<TopBarState> = mutableStateOf(TopBarState())

}
