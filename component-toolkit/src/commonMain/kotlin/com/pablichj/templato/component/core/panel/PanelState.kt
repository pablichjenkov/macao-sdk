package com.pablichj.templato.component.core.panel

import com.pablichj.templato.component.core.NavItemDeco
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface IPanelState {
    /**
     * Intended for the Composable NavBar to render the List if NavBarItems items
     * */
    val navItemsFlow: Flow<List<NavItemDeco>>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: Flow<NavItemDeco>

    var panelHeaderState: PanelHeaderState

    /**
     * Intended to be called from the Composable NavBar item click events
     * */
    fun navItemClick(navbarItem: NavItemDeco)

    fun setNavItemsDeco(navItemsDeco: List<NavItemDeco>)

    /**
     * Intended to be called from a client class to select a navItem in the NavBar
     * */
    fun selectNavItemDeco(navbarItem: NavItemDeco)
}

class PanelState(
    private val coroutineScope: CoroutineScope,
    override var panelHeaderState: PanelHeaderState,
    private var navItemsDeco: List<NavItemDeco>
) : IPanelState {

    private val _navItemsFlow = MutableStateFlow(navItemsDeco)
    override val navItemsFlow: StateFlow<List<NavItemDeco>> = _navItemsFlow.asStateFlow()

    private val _navItemClickFlow = MutableSharedFlow<NavItemDeco>()
    override val navItemClickFlow: SharedFlow<NavItemDeco> = _navItemClickFlow.asSharedFlow()

    override fun navItemClick(navbarItem: NavItemDeco) {
        coroutineScope.launch {
            _navItemClickFlow.emit(navbarItem)
        }
    }

    override fun setNavItemsDeco(navItemsDeco: List<NavItemDeco>) {
        this.navItemsDeco = navItemsDeco
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItemDeco(navbarItem: NavItemDeco) {
        updateNavBarSelectedItem(navbarItem)
    }

    private fun updateNavBarSelectedItem(panelNavItem: NavItemDeco) {
        _navItemsFlow.update {
            navItemsDeco.map { navItemDeco ->
                navItemDeco.copy(
                    selected = panelNavItem == navItemDeco
                )
            }
        }
    }

}