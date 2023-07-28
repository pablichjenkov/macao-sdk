package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.pablichj.templato.component.demo.CustomTopBarComponent
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.drawer.DrawerStatePresenterDefault
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarStatePresenterDefault
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.DispatchersProxy

object DrawerTreeBuilder {
    private val diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)
    private lateinit var drawerComponent: DrawerComponent<DrawerStatePresenterDefault>

    fun build(): DrawerComponent<DrawerStatePresenterDefault> {

        if (DrawerTreeBuilder::drawerComponent.isInitialized) {
            return drawerComponent
        }

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Home",
                    TopBarComponent.DefaultConfig,
                    {},
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
                component = CustomTopBarComponent(
                    "Settings",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            )
        )

        return DrawerComponent(
            drawerStatePresenter = DrawerComponent.createDefaultDrawerStatePresenter(),
            config = DrawerComponent.DefaultConfig,
            dispatchers = DispatchersProxy.DefaultDispatchers,
            content = DrawerComponent.DefaultDrawerComponentView
        ).also {
            drawerComponent = it
            it.setNavItems(drawerNavItems, 0)
        }
    }

    private fun buildNavBarNode(): NavBarComponent<NavBarStatePresenterDefault> {

        val NavBarNode = NavBarComponent(
            navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
            config = NavBarComponent.DefaultConfig,
            content = NavBarComponent.DefaultNavBarComponentView
        )

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Active",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Orders/Active",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                component = CustomTopBarComponent(
                    "Orders/Past",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            ),
            NavItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                component = CustomTopBarComponent(
                    "Orders/New Order",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            )
        )

        return NavBarNode.also { it.setNavItems(navbarNavItems, 0) }
    }

}