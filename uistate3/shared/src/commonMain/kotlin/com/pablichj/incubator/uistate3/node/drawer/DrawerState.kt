package com.pablichj.incubator.uistate3.node.drawer

import androidx.compose.material.DrawerValue
import com.pablichj.incubator.uistate3.node.NavItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

interface INavigationDrawerState {
    /**
     * Intended for the Composable NavigationDrawer to render the List if NavDrawer items
     * */
    val navItemsFlow: Flow<List<NavItem>>

    /**
     * Intended for the Composable NavigationDrawer to close open/close the Drawer pane
     * */
    val drawerOpenFlow: Flow<DrawerValue>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: Flow<NavItem>

    /**
     * Intended to be called from the Composable NavigationDrawer item click events
     * */
    fun navItemClick(drawerNavItem: NavItem)

    /**
     * Intended to be called from a client class to select a navItem in the drawer
     * */
    fun selectNavItem(drawerNavItem: NavItem)

    /**
     * Intended to be called from a client class to toggle the drawer visibility
     * */
    fun setDrawerState(drawerValue: DrawerValue)
}

class NavigationDrawerState /*@Inject */ constructor(
    //val dispatchersBin: DispatchersBin
    var navItems: List<NavItem>
) : INavigationDrawerState {

    // TODO: Use DispatchersBin
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _navItemsFlow = MutableStateFlow<List<NavItem>>(emptyList())
    override val navItemsFlow: Flow<List<NavItem>>
        get() = _navItemsFlow

    private val _drawerOpenFlow = MutableSharedFlow<DrawerValue>()
    override val drawerOpenFlow: Flow<DrawerValue>
        get() = _drawerOpenFlow

    private val _navItemClickFlow = MutableSharedFlow<NavItem>()
    override val navItemClickFlow: Flow<NavItem>
        get() = _navItemClickFlow

    init {
        _navItemsFlow.value = navItems
    }

    override fun navItemClick(drawerNavItem: NavItem) {
        coroutineScope.launch {
            _drawerOpenFlow.emit(DrawerValue.Closed)
            _navItemClickFlow.emit(drawerNavItem)
        }
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItem(drawerNavItem: NavItem) {
        coroutineScope.launch {
            updateDrawerSelectedItem(drawerNavItem)
        }
    }

    override fun setDrawerState(drawerValue: DrawerValue) {
        coroutineScope.launch {
            _drawerOpenFlow.emit(drawerValue)
        }
    }

    private suspend fun updateDrawerSelectedItem(drawerNavItem: NavItem) {
        navItems = navItems.map {
            it.copy().apply { selected = drawerNavItem.component == it.component }
        }
        _navItemsFlow.emit(navItems)
    }

}