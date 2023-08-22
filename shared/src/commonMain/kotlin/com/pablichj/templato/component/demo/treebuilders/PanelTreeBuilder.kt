package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarStatePresenterDefault
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.core.panel.PanelStatePresenterDefault
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.demo.CustomTopBarComponent

object PanelTreeBuilder {

    private lateinit var panelComponent: PanelComponent<PanelStatePresenterDefault>

    fun build(): PanelComponent<PanelStatePresenterDefault> {

        if (PanelTreeBuilder::panelComponent.isInitialized) {
            return panelComponent
        }

        val panelNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Home",
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
                    {},
                ),
            )
        )

        return PanelComponent(
            panelStatePresenter = PanelComponent.createDefaultPanelStatePresenter(),
            content = PanelComponent.DefaultPanelComponentView
        ).also {
            it.setNavItems(panelNavItems, 0)
            panelComponent = it
        }
    }

    private fun buildNavBarNode(): NavBarComponent<NavBarStatePresenterDefault> {

        val NavBarNode = NavBarComponent(
            navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
            content = NavBarComponent.DefaultNavBarComponentView
        )

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Home",
                    {},
                )
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                component = CustomTopBarComponent(
                    "Orders",
                    {},
                )
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = CustomTopBarComponent(
                    "Settings",
                    {},
                )
            )
        )

        return NavBarNode.also { it.setNavItems(navbarNavItems, 0) }
    }

}