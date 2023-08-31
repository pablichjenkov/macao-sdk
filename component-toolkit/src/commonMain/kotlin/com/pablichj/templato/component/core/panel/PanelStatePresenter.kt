package com.pablichj.templato.component.core.panel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface PanelStatePresenter {
    /**
     * Intended for the Composable NavBar to render the List if NavBarItems items
     * */
    val navItemsState: State<List<PanelNavItem>>

    val panelStyle: PanelStyle

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: SharedFlow<PanelNavItem>

    val panelHeaderState: State<PanelHeaderState>

    /**
     * Intended to be called from the Composable NavBar item click events
     * */
    fun navItemClick(navbarItem: PanelNavItem)

    fun setNavItemsDeco(navItemDecoList: List<PanelNavItem>)

    /**
     * Intended to be called from a client class to select a navItem in the NavBar
     * */
    fun selectNavItemDeco(navbarItem: PanelNavItem)
}

class PanelStatePresenterDefault(
    dispatcher: CoroutineDispatcher,
    panelHeaderState: PanelHeaderState,
    override val panelStyle: PanelStyle = PanelStyle(),
    navItemDecoList: List<PanelNavItem> = emptyList()
) : PanelStatePresenter {

    private val coroutineScope = CoroutineScope(dispatcher)

    private val _navItemsState = mutableStateOf(navItemDecoList)
    override val navItemsState: State<List<PanelNavItem>> = _navItemsState

    override val panelHeaderState: State<PanelHeaderState> = mutableStateOf(panelHeaderState)

    private val _navItemClickFlow = MutableSharedFlow<PanelNavItem>()
    override val navItemClickFlow: SharedFlow<PanelNavItem> = _navItemClickFlow.asSharedFlow()

    override fun navItemClick(navbarItem: PanelNavItem) {
        coroutineScope.launch {
            _navItemClickFlow.emit(navbarItem)
        }
    }

    override fun setNavItemsDeco(navItemDecoList: List<PanelNavItem>) {
        _navItemsState.value = navItemDecoList
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItemDeco(navbarItem: PanelNavItem) {
        updateNavBarSelectedItem(navbarItem)
    }

    private fun updateNavBarSelectedItem(panelNavItem: PanelNavItem) {
        val update = _navItemsState.value.map { navItemDeco ->
            navItemDeco.copy(
                selected = panelNavItem.component == navItemDeco.component
            )
        }
        _navItemsState.value = update
    }

}
