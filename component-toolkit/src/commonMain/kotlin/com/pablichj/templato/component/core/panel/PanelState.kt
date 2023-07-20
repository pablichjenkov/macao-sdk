package com.pablichj.templato.component.core.panel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.pablichj.templato.component.core.NavItemDeco
import com.pablichj.templato.component.core.drawer.DrawerHeaderState
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

interface PanelState {
    /**
     * Intended for the Composable NavBar to render the List if NavBarItems items
     * */
    val navItemsFlow: StateFlow<List<NavItemDeco>>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: SharedFlow<NavItemDeco>

    val panelHeaderState: State<PanelHeaderState>

    /**
     * Intended to be called from the Composable NavBar item click events
     * */
    fun navItemClick(navbarItem: NavItemDeco)

    fun setNavItemsDeco(navItemDecoList: List<NavItemDeco>)

    /**
     * Intended to be called from a client class to select a navItem in the NavBar
     * */
    fun selectNavItemDeco(navbarItem: NavItemDeco)
}

class PanelStateDefault(
    dispatcher: CoroutineDispatcher,
    panelHeaderState: PanelHeaderState,
    var navItemDecoList: List<NavItemDeco> = emptyList()
) : PanelState {

    private val coroutineScope = CoroutineScope(dispatcher)

    private val _navItemsFlow = MutableStateFlow(navItemDecoList)
    override val navItemsFlow: StateFlow<List<NavItemDeco>> = _navItemsFlow.asStateFlow()

    override val panelHeaderState: State<PanelHeaderState> = mutableStateOf(panelHeaderState)

    private val _navItemClickFlow = MutableSharedFlow<NavItemDeco>()
    override val navItemClickFlow: SharedFlow<NavItemDeco> = _navItemClickFlow.asSharedFlow()

    override fun navItemClick(navbarItem: NavItemDeco) {
        coroutineScope.launch {
            _navItemClickFlow.emit(navbarItem)
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
    override fun selectNavItemDeco(navbarItem: NavItemDeco) {
        updateNavBarSelectedItem(navbarItem)
    }

    private fun updateNavBarSelectedItem(panelNavItem: NavItemDeco) {
        _navItemsFlow.update {// navItemDecoList -> bug the first time is opened
            navItemDecoList.map { navItemDeco ->
                navItemDeco.copy(
                    selected = panelNavItem == navItemDeco
                )
            }
        }
    }

}
