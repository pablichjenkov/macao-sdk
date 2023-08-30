package com.pablichj.templato.component.core.navbar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface NavBarStatePresenter {
    /**
     * Intended for the Composable NavBar to render the List if NavBarItems items
     * */
    val navItemsState: State<List<NavBarNavItem>>

    val navBarStyle: NavBarStyle

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: SharedFlow<NavBarNavItem>

    /**
     * Intended to be called from the Composable NavBar item click events
     * */
    fun navItemClick(navbarItem: NavBarNavItem)

    fun setNavItemsDeco(navItemDecoList: List<NavBarNavItem>)

    /**
     * Intended to be called from a client class to select a navItem in the NavBar
     * */
    fun selectNavItemDeco(navbarItem: NavBarNavItem)
}

class NavBarStatePresenterDefault(
    dispatcher: CoroutineDispatcher,
    override val navBarStyle: NavBarStyle = NavBarStyle(),
    navItemDecoList: List<NavBarNavItem> = emptyList()
) : NavBarStatePresenter {

    private val coroutineScope = CoroutineScope(dispatcher)

    private val _navItemsState = mutableStateOf(navItemDecoList)
    override val navItemsState: State<List<NavBarNavItem>> = _navItemsState

    private val _navItemClickFlow = MutableSharedFlow<NavBarNavItem>()
    override val navItemClickFlow: SharedFlow<NavBarNavItem> = _navItemClickFlow.asSharedFlow()

    override fun navItemClick(navbarItem: NavBarNavItem) {
        coroutineScope.launch {
            _navItemClickFlow.emit(navbarItem)
        }
    }

    override fun setNavItemsDeco(navItemDecoList: List<NavBarNavItem>) {
        _navItemsState.value = navItemDecoList
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItemDeco(navbarItem: NavBarNavItem) {
        updateNavBarSelectedItem(navbarItem)
    }

    private fun updateNavBarSelectedItem(navbarItem: NavBarNavItem) {
        val update = _navItemsState.value.map { navItemDeco ->
            navItemDeco.copy(
                selected = navbarItem.component == navItemDeco.component
            )
        }
        _navItemsState.value = update
    }

}
