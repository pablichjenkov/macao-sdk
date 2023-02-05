package com.pablichj.incubator.uistate3.node.panel

import com.pablichj.incubator.uistate3.node.NavItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

interface IPanelState {
    /**
     * Intended for the Composable NavBar to render the List if NavBarItems items
     * */
    val navItemsFlow: Flow<List<NavItem>>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: Flow<NavItem>

    /**
     * Intended to be called from the Composable NavBar item click events
     * */
    fun navItemClick(navbarItem: NavItem)

    /**
     * Intended to be called from a client class to select a navItem in the NavBar
     * */
    fun selectNavItem(navbarItem: NavItem)
}

class PanelState /*@Inject */ constructor(
    //val dispatchersBin: DispatchersBin
    var navItems: List<NavItem>
) : IPanelState {

    // TODO: Use DispatchersBin
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _navItemsFlow = MutableStateFlow<List<NavItem>>(emptyList())
    override val navItemsFlow: Flow<List<NavItem>>
        get() = _navItemsFlow

    private val _navItemClickFlow = MutableSharedFlow<NavItem>()
    override val navItemClickFlow: Flow<NavItem>
        get() = _navItemClickFlow

    init {
        _navItemsFlow.value = navItems
    }

    override fun navItemClick(navbarItem: NavItem) {
        coroutineScope.launch {
            _navItemClickFlow.emit(navbarItem)
        }
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItem(navbarItem: NavItem) {
        coroutineScope.launch {
            updateNavBarSelectedItem(navbarItem)
        }
    }

    private suspend fun updateNavBarSelectedItem(navbarItem: NavItem) {
        navItems = navItems.map {
            it.copy().apply { selected = navbarItem.component == it.component }
        }
        _navItemsFlow.emit(navItems)
    }

}