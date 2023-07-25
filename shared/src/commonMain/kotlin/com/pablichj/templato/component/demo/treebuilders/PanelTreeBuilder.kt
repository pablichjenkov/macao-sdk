package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarStateDefault
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.core.panel.PanelStateDefault
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.demo.CustomTopBarComponent

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
                component = CustomTopBarComponent("Home", TopBarComponent.DefaultConfig) {},

                ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = buildNavBarNode(),

                ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent("Settings", TopBarComponent.DefaultConfig) {},

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
                component = CustomTopBarComponent("Home", TopBarComponent.DefaultConfig) {},

                ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                component = CustomTopBarComponent("Orders", TopBarComponent.DefaultConfig) {},

                ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = CustomTopBarComponent("Settings", TopBarComponent.DefaultConfig) {},

                )
        )

        return NavBarNode.also { it.setNavItems(navbarNavItems, 0) }
    }

}