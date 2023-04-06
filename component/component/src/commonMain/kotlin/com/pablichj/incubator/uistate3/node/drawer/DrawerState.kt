package com.pablichj.incubator.uistate3.node.drawer

import androidx.compose.material.DrawerValue
import com.pablichj.incubator.uistate3.node.NavItemDeco
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

interface INavigationDrawerState {
    /**
     * Intended for the Composable NavigationDrawer to render the List if NavDrawer items
     * */
    val navItemsFlow: Flow<List<NavItemDeco>>

    /**
     * Intended for the Composable NavigationDrawer to close open/close the Drawer pane
     * */
    val drawerOpenFlow: Flow<DrawerValue>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: Flow<NavItemDeco>

    var drawerHeaderState: DrawerHeaderState

    /**
     * Intended to be called from the Composable NavigationDrawer item click events
     * */
    fun navItemClick(drawerNavItem: NavItemDeco)

    /**
     * Intended to be called from a client class to select a navItem in the drawer
     * */
    fun selectNavItemDeco(drawerNavItem: NavItemDeco)

    /**
     * Intended to be called from a client class to toggle the drawer visibility
     * */
    fun setDrawerState(drawerValue: DrawerValue)
}

class NavigationDrawerState(
    private val coroutineScope: CoroutineScope,
    override var drawerHeaderState: DrawerHeaderState,
    var navItemsDeco: List<NavItemDeco>
) : INavigationDrawerState {

    private val _navItemsFlow = MutableStateFlow<List<NavItemDeco>>(emptyList())
    override val navItemsFlow: Flow<List<NavItemDeco>>
        get() = _navItemsFlow

    private val _drawerOpenFlow = MutableSharedFlow<DrawerValue>()
    override val drawerOpenFlow: Flow<DrawerValue>
        get() = _drawerOpenFlow

    private val _navItemClickFlow = MutableSharedFlow<NavItemDeco>()
    override val navItemClickFlow: Flow<NavItemDeco>
        get() = _navItemClickFlow

    init {
        _navItemsFlow.value = navItemsDeco
    }

    override fun navItemClick(drawerNavItem: NavItemDeco) {
        coroutineScope.launch {
            _drawerOpenFlow.emit(DrawerValue.Closed)
            _navItemClickFlow.emit(drawerNavItem)
        }
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItemDeco(drawerNavItem: NavItemDeco) {
        coroutineScope.launch {
            updateDrawerSelectedItem(drawerNavItem)
        }
    }

    override fun setDrawerState(drawerValue: DrawerValue) {
        coroutineScope.launch {
            _drawerOpenFlow.emit(drawerValue)
        }
    }

    private suspend fun updateDrawerSelectedItem(drawerNavItem: NavItemDeco) {
        navItemsDeco = navItemsDeco.map {
            it.copy().apply { selected = drawerNavItem.component == it.component }
        }
        _navItemsFlow.emit(navItemsDeco)
    }

}