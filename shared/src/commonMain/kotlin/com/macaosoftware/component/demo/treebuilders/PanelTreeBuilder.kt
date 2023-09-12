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
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.navbar.NavBarStatePresenterDefault
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.component.panel.PanelStatePresenterDefault
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults

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

        return PanelComponent(
            panelStatePresenter = PanelComponentDefaults.createPanelStatePresenter(),
            componentViewModel = PanelComponentDefaults.createComponentViewModel(),
            content = PanelComponentDefaults.PanelComponentView
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
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Home", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Orders", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Settings", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )

        return NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentViewModel = NavBarComponentDefaults.createComponentViewModel(),
            content = NavBarComponentDefaults.NavBarComponentView
        ).also { it.setNavItems(navbarNavItems, 0) }
    }

}