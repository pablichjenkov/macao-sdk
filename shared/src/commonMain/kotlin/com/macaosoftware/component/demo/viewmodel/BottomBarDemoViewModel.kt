package com.macaosoftware.component.demo.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentViewModel
import com.macaosoftware.component.navbar.NavBarStatePresenterDefault
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults

class BottomBarDemoViewModel : NavBarComponentViewModel<NavBarStatePresenterDefault>() {

    private lateinit var navBarComponent: NavBarComponent<NavBarStatePresenterDefault>
    private var navBarItemsCache: MutableList<NavItem>? = null

    override fun create(navBarComponent: NavBarComponent<NavBarStatePresenterDefault>) {
        this.navBarComponent = navBarComponent
        val navBarItems = createNavBarItems()
        val selectedIndex = 0
        navBarComponent.setNavItems(navBarItems, selectedIndex)
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    private fun createNavBarItems(): MutableList<NavItem> {
        navBarItemsCache?.let {
            return it
        }
        return mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Home", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Orders", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Settings", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        ).also {
            navBarItemsCache = it
        }
    }

}
