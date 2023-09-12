package com.macaosoftware.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.demo.componentDelegates.Demo3PageTopBarViewModel
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.navbar.NavBarStatePresenterDefault
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults

object DrawerTreeBuilder {
    private lateinit var drawerComponent: DrawerComponent<DrawerStatePresenterDefault>

    fun build(): DrawerComponent<DrawerStatePresenterDefault> {

        if (DrawerTreeBuilder::drawerComponent.isInitialized) {
            return drawerComponent
        }

        val drawerNavItems = mutableListOf(
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
                icon = Icons.Filled.Refresh,
                component = buildNavBarNode(),
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Settings", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )

        return DrawerComponent(
            drawerStatePresenter = DrawerComponentDefaults.createDrawerStatePresenter(),
            componentViewModel = DrawerComponentDefaults.createComponentViewModel(),
            content = DrawerComponentDefaults.DrawerComponentView
        ).also {
            drawerComponent = it
            it.setNavItems(drawerNavItems, 0)
        }
    }

    private fun buildNavBarNode(): NavBarComponent<NavBarStatePresenterDefault> {

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Active",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Active", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Past", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("New Order", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentViewModel = NavBarComponentDefaults.createComponentViewModel(),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        return navBarComponent.also { it.setNavItems(navbarNavItems, 0) }
    }

}
