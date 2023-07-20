package com.pablichj.templato.component.core.drawer

import androidx.compose.material.DrawerValue
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.pablichj.templato.component.core.NavItemDeco
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface NavigationDrawerState {
    /**
     * Intended for the Composable NavigationDrawer to render the List if NavDrawer items
     * */
    val navItemsFlow: StateFlow<List<NavItemDeco>>

    /**
     * Intended for the Composable NavigationDrawer to close open/close the Drawer pane
     * */
    val drawerOpenFlow: SharedFlow<DrawerValue>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: SharedFlow<NavItemDeco>

    val drawerHeaderState: State<DrawerHeaderState>

    /**
     * Intended to be called from the Composable NavigationDrawer item click events
     * */
    fun navItemClick(drawerNavItem: NavItemDeco)

    fun setNavItemsDeco(navItemDecoList: List<NavItemDeco>)

    /**
     * Intended to be called from a client class to select a navItem in the drawer
     * */
    fun selectNavItemDeco(drawerNavItem: NavItemDeco)

    /**
     * Intended to be called from a client class to toggle the drawer visibility
     * */
    fun setDrawerState(drawerValue: DrawerValue)
}

class NavigationDrawerStateDefault(
    dispatcher: CoroutineDispatcher,
    drawerHeaderState: DrawerHeaderState,
    var navItemDecoList: List<NavItemDeco> = emptyList()
) : NavigationDrawerState {

    private val coroutineScope = CoroutineScope(dispatcher)

    private val _navItemsFlow = MutableStateFlow(navItemDecoList)
    override val navItemsFlow: StateFlow<List<NavItemDeco>> = _navItemsFlow.asStateFlow()

    override val drawerHeaderState: State<DrawerHeaderState> = mutableStateOf(drawerHeaderState)

    private val _drawerOpenFlow = MutableSharedFlow<DrawerValue>()
    override val drawerOpenFlow: SharedFlow<DrawerValue> = _drawerOpenFlow.asSharedFlow()

    private val _navItemClickFlow = MutableSharedFlow<NavItemDeco>()
    override val navItemClickFlow: SharedFlow<NavItemDeco> = _navItemClickFlow.asSharedFlow()

    override fun navItemClick(drawerNavItem: NavItemDeco) {
        coroutineScope.launch {
            _drawerOpenFlow.emit(DrawerValue.Closed)
            _navItemClickFlow.emit(drawerNavItem)
        }
    }

    override fun setDrawerState(drawerValue: DrawerValue) {
        coroutineScope.launch {
            _drawerOpenFlow.emit(drawerValue)
        }
    }

    override fun setNavItemsDeco(navItemDecoList: List<NavItemDeco>) {
        // _navItemsFlow.update { navItemDecoList }
        // todo: investigate bug not selecting/selected the first time is opened
        this.navItemDecoList = navItemDecoList
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItemDeco(drawerNavItem: NavItemDeco) {
        updateDrawerSelectedItem(drawerNavItem)
    }

    private fun updateDrawerSelectedItem(drawerNavItem: NavItemDeco) {
        _navItemsFlow.update { // navItemDecoList -> bug not selecting/selected the first time is opened
            navItemDecoList.map { navItemDeco ->
                navItemDeco.copy(
                    selected = drawerNavItem == navItemDeco
                )
            }
        }
    }

}
