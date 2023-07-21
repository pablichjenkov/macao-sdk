package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarStateDefault
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.core.panel.PanelState
import com.pablichj.templato.component.core.panel.PanelStateDefault
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.demo.CustomTopBarComponent
import com.pablichj.templato.component.core.stack.StackComponent

object PanelTreeBuilder {

    private lateinit var panelComponent: PanelComponent<PanelStateDefault>

    fun build(): PanelComponent<PanelStateDefault> {

        if (PanelTreeBuilder::panelComponent.isInitialized) {
            return panelComponent
        }

        val panelNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home", StackComponent.DefaultConfig) {},

                ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = buildNavBarNode(),

                ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent("Settings", StackComponent.DefaultConfig) {},

                )
        )

        return PanelComponent(
            panelState = PanelComponent.createDefaultState(),
            config = PanelComponent.DefaultConfig,
            content = PanelComponent.DefaultPanelComponentView
        ).also {
            it.setNavItems(panelNavItems, 0)
            panelComponent = it
        }
    }

    private fun buildNavBarNode(): NavBarComponent<NavBarStateDefault> {

        val NavBarNode = NavBarComponent(
            navBarState = NavBarComponent.createDefaultState(),
            config = NavBarComponent.DefaultConfig,
            content = NavBarComponent.DefaultNavBarComponentView
        )

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent("Home", StackComponent.DefaultConfig) {},

                ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                component = CustomTopBarComponent("Orders", StackComponent.DefaultConfig) {},

                ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = CustomTopBarComponent("Settings", StackComponent.DefaultConfig) {},

                )
        )

        return NavBarNode.also { it.setNavItems(navbarNavItems, 0) }
    }

}