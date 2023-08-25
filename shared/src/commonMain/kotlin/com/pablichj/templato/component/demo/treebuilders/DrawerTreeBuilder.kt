package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.drawer.DrawerComponentDefaults
import com.pablichj.templato.component.core.drawer.DrawerStatePresenterDefault
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarComponentDefaults
import com.pablichj.templato.component.core.navbar.NavBarStatePresenterDefault
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.demo.componentDelegates.DrawerComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.NavBarComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.TopBarComponentDelegate1

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
                    topBarStatePresenter = TopBarComponent.createDefaultTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Home", {}),
                    content = TopBarComponent.DefaultTopBarComponentView
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
                    topBarStatePresenter = TopBarComponent.createDefaultTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Settings", {}),
                    content = TopBarComponent.DefaultTopBarComponentView
                )
            )
        )

        return DrawerComponent(
            drawerStatePresenter = DrawerComponentDefaults.createDrawerStatePresenter(),
            componentDelegate = DrawerComponentDelegate1(drawerNavItems),
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
                    topBarStatePresenter = TopBarComponent.createDefaultTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Active", {}),
                    content = TopBarComponent.DefaultTopBarComponentView
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponent.createDefaultTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Past", {}),
                    content = TopBarComponent.DefaultTopBarComponentView
                )
            ),
            NavItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponent.createDefaultTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("New Order", {}),
                    content = TopBarComponent.DefaultTopBarComponentView
                )
            )
        )

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentDelegate = NavBarComponentDelegate1(navbarNavItems),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        return navBarComponent.also { it.setNavItems(navbarNavItems, 0) }
    }

}
