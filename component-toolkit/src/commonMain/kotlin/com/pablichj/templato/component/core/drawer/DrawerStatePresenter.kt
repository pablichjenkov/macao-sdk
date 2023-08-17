package com.pablichj.templato.component.core.drawer

import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.pablichj.templato.component.core.NavItemDeco
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface DrawerStatePresenter {
    /**
     * Intended for the Composable NavigationDrawer to render the List if NavDrawer items
     * */
    val navItemsState: State<List<NavItemDeco>>

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

class DrawerStatePresenterDefault(
    dispatcher: CoroutineDispatcher,
    drawerHeaderState: DrawerHeaderState,
    navItemDecoList: List<NavItemDeco> = emptyList()
) : DrawerStatePresenter {

    private val coroutineScope = CoroutineScope(dispatcher)

    private var _navItemsState = mutableStateOf(navItemDecoList)
    override val navItemsState: State<List<NavItemDeco>> = _navItemsState

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
        _navItemsState.value = navItemDecoList
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItemDeco(drawerNavItem: NavItemDeco) {
        updateDrawerSelectedItem(drawerNavItem)
    }

    private fun updateDrawerSelectedItem(drawerNavItem: NavItemDeco) {
        val update = _navItemsState.value.map { navItemDeco ->
            navItemDeco.copy(
                selected = drawerNavItem.component == navItemDeco.component
            )
        }
        _navItemsState.value = update
    }

}
