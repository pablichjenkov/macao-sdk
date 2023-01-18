package com.pablichj.incubator.uistate3.node.drawer

import androidx.compose.material.DrawerValue
import com.pablichj.incubator.uistate3.node.NodeItem
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
    val navItemsFlow: Flow<List<NodeItem>>

    /**
     * Intended for the Composable NavigationDrawer to close open/close the Drawer pane
     * */
    val drawerOpenFlow: Flow<DrawerValue>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: Flow<NodeItem>

    /**
     * Intended to be called from the Composable NavigationDrawer item click events
     * */
    fun navItemClick(drawerNavItem: NodeItem)

    /**
     * Intended to be called from a client class to select a navItem in the drawer
     * */
    fun selectNavItem(drawerNavItem: NodeItem)

    /**
     * Intended to be called from a client class to toggle the drawer visibility
     * */
    fun setDrawerState(drawerValue: DrawerValue)
}

class NavigationDrawerState /*@Inject */ constructor(
    //val dispatchersBin: DispatchersBin
    var navItems: List<NodeItem>
) : INavigationDrawerState {

    // TODO: Use DispatchersBin
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _navItemsFlow = MutableStateFlow<List<NodeItem>>(emptyList())
    override val navItemsFlow: Flow<List<NodeItem>>
        get() = _navItemsFlow

    private val _drawerOpenFlow = MutableSharedFlow<DrawerValue>()
    override val drawerOpenFlow: Flow<DrawerValue>
        get() = _drawerOpenFlow

    private val _navItemClickFlow = MutableSharedFlow<NodeItem>()
    override val navItemClickFlow: Flow<NodeItem>
        get() = _navItemClickFlow

    init {
        _navItemsFlow.value = navItems
    }

    override fun navItemClick(drawerNavItem: NodeItem) {
        coroutineScope.launch {
            _drawerOpenFlow.emit(DrawerValue.Closed)
            _navItemClickFlow.emit(drawerNavItem)
        }
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItem(drawerNavItem: NodeItem) {
        coroutineScope.launch {
            updateDrawerSelectedItem(drawerNavItem)
        }
    }

    override fun setDrawerState(drawerValue: DrawerValue) {
        coroutineScope.launch {
            _drawerOpenFlow.emit(drawerValue)
        }
    }

    private suspend fun updateDrawerSelectedItem(drawerNavItem: NodeItem) {
        navItems = navItems.map {
            it.copy().apply { selected = drawerNavItem.node == it.node }
        }
        _navItemsFlow.emit(navItems)
    }

}