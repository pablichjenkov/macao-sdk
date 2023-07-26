package com.pablichj.templato.component.core.panel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.pablichj.templato.component.core.NavItemDeco
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
    val navItemsState: State<List<NavItemDeco>>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: SharedFlow<NavItemDeco>

    val panelHeaderState: State<PanelHeaderState>

    /**
     * Intended to be called from the Composable NavBar item click events
     * */
    fun navItemClick(navbarItem: NavItemDeco)

    fun setNavItemsDeco(navItemDecoList: List<NavItemDeco>)

    /**
     * Intended to be called from a client class to select a navItem in the NavBar
     * */
    fun selectNavItemDeco(navbarItem: NavItemDeco)
}

class PanelStatePresenterDefault(
    dispatcher: CoroutineDispatcher,
    panelHeaderState: PanelHeaderState,
    navItemDecoList: List<NavItemDeco> = emptyList()
) : PanelStatePresenter {

    private val coroutineScope = CoroutineScope(dispatcher)

    private val _navItemsState = mutableStateOf(navItemDecoList)
    override val navItemsState: State<List<NavItemDeco>> = _navItemsState

    override val panelHeaderState: State<PanelHeaderState> = mutableStateOf(panelHeaderState)

    private val _navItemClickFlow = MutableSharedFlow<NavItemDeco>()
    override val navItemClickFlow: SharedFlow<NavItemDeco> = _navItemClickFlow.asSharedFlow()

    override fun navItemClick(navbarItem: NavItemDeco) {
        coroutineScope.launch {
            _navItemClickFlow.emit(navbarItem)
        }
    }

    override fun setNavItemsDeco(navItemDecoList: List<NavItemDeco>) {
        _navItemsState.value = navItemDecoList
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItemDeco(navbarItem: NavItemDeco) {
        updateNavBarSelectedItem(navbarItem)
    }

    private fun updateNavBarSelectedItem(panelNavItem: NavItemDeco) {
        val update = _navItemsState.value.map { navItemDeco ->
            navItemDeco.copy(
                selected = panelNavItem.component == navItemDeco.component
            )
        }
        _navItemsState.value = update
    }

}
