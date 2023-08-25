package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarComponentDefaults
import com.pablichj.templato.component.core.navbar.NavBarStatePresenterDefault
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.core.panel.PanelStatePresenterDefault
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.demo.componentDelegates.NavBarComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.PanelComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.TopBarComponentDelegate1

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

        return PanelComponent(
            panelStatePresenter = PanelComponent.createDefaultPanelStatePresenter(),
            componentDelegate = PanelComponentDelegate1(panelNavItems),
            content = PanelComponent.DefaultPanelComponentView
        ).also {
            it.setNavItems(panelNavItems, 0)
            panelComponent = it
        }
    }

    private fun buildNavBarNode(): NavBarComponent<NavBarStatePresenterDefault> {

        val navbarNavItems = mutableListOf(
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
                icon = Icons.Filled.Settings,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponent.createDefaultTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Orders", {}),
                    content = TopBarComponent.DefaultTopBarComponentView
                )
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponent.createDefaultTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Settings", {}),
                    content = TopBarComponent.DefaultTopBarComponentView
                )
            )
        )

        return NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentDelegate = NavBarComponentDelegate1(navbarNavItems),
            content = NavBarComponentDefaults.NavBarComponentView
        ).also { it.setNavItems(navbarNavItems, 0) }
    }

}