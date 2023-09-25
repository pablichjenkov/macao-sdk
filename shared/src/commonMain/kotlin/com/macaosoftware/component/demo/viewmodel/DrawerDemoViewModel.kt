package com.macaosoftware.component.demo.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.demo.viewmodel.factory.BottomNavigationDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.Demo3PageTopBarViewModelFactory
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentViewModel
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.navbar.NavBarStatePresenterDefault
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults

class DrawerDemoViewModel(
    drawerComponent: DrawerComponent<DrawerStatePresenterDefault>,
    override val drawerStatePresenter: DrawerStatePresenterDefault
) : DrawerComponentViewModel<DrawerStatePresenterDefault>(drawerComponent) {

    override fun onCreate() {
        val drawerNavItems = createDrawerItems()
        val selectedIndex = 0
        this.drawerComponent.setNavItems(drawerNavItems, selectedIndex)
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    private fun createDrawerItems(): MutableList<NavItem> {
        return mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Home",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = buildNavBarComponent(),
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Settings",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )
    }

    private fun buildNavBarComponent(): NavBarComponent<NavBarStatePresenterDefault> {

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Active",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Active",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Past",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "New Order",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )

        val navBarComponent = NavBarComponent(
            viewModelFactory = BottomNavigationDemoViewModelFactory(
                NavBarComponentDefaults.createNavBarStatePresenter()
            ),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        return navBarComponent.also { it.setNavItems(navbarNavItems, 0) }
    }

}
