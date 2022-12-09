package com.pablichj.encubator.node.navbar

import com.pablichj.encubator.node.NavigatorNodeItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

interface INavBarState {
    /**
     * Intended for the Composable NavBar to render the List if NavBarItems items
     * */
    val navItemsFlow: Flow<List<NavigatorNodeItem>>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: Flow<NavigatorNodeItem>

    /**
     * Intended to be called from the Composable NavBar item click events
     * */
    fun navItemClick(navbarItem: NavigatorNodeItem)

    /**
     * Intended to be called from a client class to select a navItem in the NavBar
     * */
    fun selectNavItem(navbarItem: NavigatorNodeItem)
}

class NavBarState /*@Inject */ constructor(
    //val dispatchersBin: DispatchersBin
    var navItems: List<NavigatorNodeItem>
) : INavBarState {

    // TODO: Use DispatchersBin
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _navItemsFlow = MutableStateFlow<List<NavigatorNodeItem>>(emptyList())
    override val navItemsFlow: Flow<List<NavigatorNodeItem>>
        get() = _navItemsFlow

    private val _navItemClickFlow = MutableSharedFlow<NavigatorNodeItem>()
    override val navItemClickFlow: Flow<NavigatorNodeItem>
        get() = _navItemClickFlow

    init {
        _navItemsFlow.value = navItems
    }

    override fun navItemClick(navbarItem: NavigatorNodeItem) {
        coroutineScope.launch {
            _navItemClickFlow.emit(navbarItem)
        }
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItem(navbarItem: NavigatorNodeItem) {
        coroutineScope.launch {
            updateNavBarSelectedItem(navbarItem)
        }
    }

    private suspend fun updateNavBarSelectedItem(navbarItem: NavigatorNodeItem) {
        navItems = navItems.map {
            it.copy().apply { selected = navbarItem.node == it.node }
        }
        _navItemsFlow.emit(navItems)
    }

}