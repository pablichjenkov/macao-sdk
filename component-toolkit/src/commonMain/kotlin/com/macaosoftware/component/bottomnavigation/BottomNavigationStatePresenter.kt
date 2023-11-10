package com.macaosoftware.component.bottomnavigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface BottomNavigationStatePresenter {
    /**
     * Intended for the Composable NavBar to render the List if NavBarItems items
     * */
    val navItemsState: State<List<BottomNavigationNavItem>>

    val bottomNavigationStyle: BottomNavigationStyle

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: SharedFlow<BottomNavigationNavItem>

    /**
     * Intended to be called from the Composable NavBar item click events
     * */
    fun navItemClick(navbarItem: BottomNavigationNavItem)

    fun setNavItemsDeco(navItemDecoList: List<BottomNavigationNavItem>)

    /**
     * Intended to be called from a client class to select a navItem in the NavBar
     * */
    fun selectNavItemDeco(navbarItem: BottomNavigationNavItem)
}

class BottomNavigationStatePresenterDefault(
    dispatcher: CoroutineDispatcher,
    override val bottomNavigationStyle: BottomNavigationStyle = BottomNavigationStyle(),
    navItemDecoList: List<BottomNavigationNavItem> = emptyList()
) : BottomNavigationStatePresenter {

    private val coroutineScope = CoroutineScope(dispatcher)

    private val _navItemsState = mutableStateOf(navItemDecoList)
    override val navItemsState: State<List<BottomNavigationNavItem>> = _navItemsState

    private val _navItemClickFlow = MutableSharedFlow<BottomNavigationNavItem>()
    override val navItemClickFlow: SharedFlow<BottomNavigationNavItem> = _navItemClickFlow.asSharedFlow()

    override fun navItemClick(navbarItem: BottomNavigationNavItem) {
        coroutineScope.launch {
            _navItemClickFlow.emit(navbarItem)
        }
    }

    override fun setNavItemsDeco(navItemDecoList: List<BottomNavigationNavItem>) {
        _navItemsState.value = navItemDecoList
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItemDeco(navbarItem: BottomNavigationNavItem) {
        updateNavBarSelectedItem(navbarItem)
    }

    private fun updateNavBarSelectedItem(navbarItem: BottomNavigationNavItem) {
        val update = _navItemsState.value.map { navItemDeco ->
            navItemDeco.copy(
                selected = navbarItem.component == navItemDeco.component
            )
        }
        _navItemsState.value = update
    }

}
