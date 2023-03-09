package com.pablichj.incubator.uistate3.node.navbar

import com.pablichj.incubator.uistate3.node.NavItemDeco
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
    val navItemsFlow: Flow<List<NavItemDeco>>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: Flow<NavItemDeco>

    /**
     * Intended to be called from the Composable NavBar item click events
     * */
    fun navItemClick(navbarItem: NavItemDeco)

    /**
     * Intended to be called from a client class to select a navItem in the NavBar
     * */
    fun selectNavItemDeco(navbarItem: NavItemDeco)
}

class NavBarState /*@Inject */ constructor(
    //val dispatchersBin: DispatchersBin
    var navItemsDeco: List<NavItemDeco>
) : INavBarState {

    // TODO: Use DispatchersBin
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _navItemsFlow = MutableStateFlow<List<NavItemDeco>>(emptyList())
    override val navItemsFlow: Flow<List<NavItemDeco>>
        get() = _navItemsFlow

    private val _navItemClickFlow = MutableSharedFlow<NavItemDeco>()
    override val navItemClickFlow: Flow<NavItemDeco>
        get() = _navItemClickFlow

    init {
        _navItemsFlow.value = navItemsDeco
    }

    override fun navItemClick(navbarItem: NavItemDeco) {
        coroutineScope.launch {
            _navItemClickFlow.emit(navbarItem)
        }
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItemDeco(navbarItem: NavItemDeco) {
        coroutineScope.launch {
            updateNavBarSelectedItem(navbarItem)
        }
    }

    private suspend fun updateNavBarSelectedItem(navbarItem: NavItemDeco) {
        navItemsDeco = navItemsDeco.map {
            it.copy().apply { selected = navbarItem.component == it.component }
        }
        _navItemsFlow.emit(navItemsDeco)
    }

}