package com.pablichj.incubator.uistate3.demo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.panel.PanelComponent
import com.pablichj.incubator.uistate3.node.setNavItems
import example.nodes.CustomTopBarComponent

object PanelTreeBuilder {

    private lateinit var PanelComponent: PanelComponent

    fun build(): PanelComponent {

        if (PanelTreeBuilder::PanelComponent.isInitialized) {
            return PanelComponent
        }

        val panelNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home", Icons.Filled.Home) {},
                selected = false
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = buildNavBarNode(),
                selected = false
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent("Settings", Icons.Filled.Email) {},
                selected = false
            )
        )

        return PanelComponent().also {
            PanelComponent = it
            it.setNavItems(panelNavItems, 0)
        }
    }

    private fun buildNavBarNode(): NavBarComponent {

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home", Icons.Filled.Home) {},
                selected = false
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                component = CustomTopBarComponent("Orders", Icons.Filled.Settings) {},
                selected = false
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = CustomTopBarComponent("Settings", Icons.Filled.Add) {},
                selected = false
            )
        )

        return NavBarComponent().also {
            it.setNavItems(navbarNavItems, 0)
        }
    }

}