package com.pablichj.templato.component.core.navbar

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

interface NavBarState {
    /**
     * Intended for the Composable NavBar to render the List if NavBarItems items
     * */
    val navItemsFlow: StateFlow<List<NavItemDeco>>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: SharedFlow<NavItemDeco>

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

class NavBarStateDefault(
    dispatcher: CoroutineDispatcher,
    private var navItemDecoList: List<NavItemDeco> = emptyList()
) : NavBarState {

    private val coroutineScope = CoroutineScope(dispatcher)

    private val _navItemsFlow = MutableStateFlow(navItemDecoList)
    override val navItemsFlow: StateFlow<List<NavItemDeco>> = _navItemsFlow.asStateFlow()

    private val _navItemClickFlow = MutableSharedFlow<NavItemDeco>()
    override val navItemClickFlow: SharedFlow<NavItemDeco> = _navItemClickFlow.asSharedFlow()

    override fun navItemClick(navbarItem: NavItemDeco) {
        coroutineScope.launch {
            _navItemClickFlow.emit(navbarItem)
        }
    }

    override fun setNavItemsDeco(navItemsDeco: List<NavItemDeco>) {
        this.navItemDecoList = navItemsDeco
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItemDeco(navbarItem: NavItemDeco) {
        updateNavBarSelectedItem(navbarItem)
    }

    private fun updateNavBarSelectedItem(navbarItem: NavItemDeco) {
        _navItemsFlow.update {
            navItemDecoList.map { navItemDeco ->
                navItemDeco.copy(
                    selected = navbarItem == navItemDeco
                )
            }
        }
    }

}
