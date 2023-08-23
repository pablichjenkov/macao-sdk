package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.drawer.DrawerStatePresenterDefault
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarStatePresenterDefault
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.demo.createCustomTopBarComponent
import com.pablichj.templato.component.platform.CoroutineDispatchers
import com.pablichj.templato.component.platform.DiContainer

object DrawerTreeBuilder {
    private val diContainer = DiContainer(CoroutineDispatchers.Defaults)
    private lateinit var drawerComponent: DrawerComponent<DrawerStatePresenterDefault>

    fun build(): DrawerComponent<DrawerStatePresenterDefault> {

        if (DrawerTreeBuilder::drawerComponent.isInitialized) {
            return drawerComponent
        }

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = createCustomTopBarComponent("Home", {})
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = buildNavBarNode(),
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = createCustomTopBarComponent("Settings", {})
            )
        )

        return DrawerComponent(
            drawerStatePresenter = DrawerComponent.createDefaultDrawerStatePresenter(),
            content = DrawerComponent.DefaultDrawerComponentView
        ).also {
            drawerComponent = it
            it.setNavItems(drawerNavItems, 0)
        }
    }

    private fun buildNavBarNode(): NavBarComponent<NavBarStatePresenterDefault> {

        val NavBarNode = NavBarComponent(
            navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
            content = NavBarComponent.DefaultNavBarComponentView
        )

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Active",
                icon = Icons.Filled.Home,
                component = createCustomTopBarComponent("Active", {})
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                component = createCustomTopBarComponent("Past", {})
            ),
            NavItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                component = createCustomTopBarComponent("New Order", {})
            )
        )

        return NavBarNode.also { it.setNavItems(navbarNavItems, 0) }
    }

}
