package com.macaosoftware.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults
import com.macaosoftware.component.demo.componentDelegates.HomeTopBarComponentViewModel
import com.macaosoftware.component.demo.componentDelegates.NavBarComponentDelegate1
import com.macaosoftware.component.demo.componentDelegates.SettingsTopBarViewModel
import com.macaosoftware.component.demo.componentDelegates.Demo3PageTopBarViewModel

object AdaptableSizeTreeBuilder {

    private lateinit var adaptiveSizeComponent: AdaptiveSizeComponent
    private lateinit var subTreeNavItems: MutableList<NavItem>

    fun build(): AdaptiveSizeComponent {
        if (AdaptableSizeTreeBuilder::adaptiveSizeComponent.isInitialized) {
            return adaptiveSizeComponent
        }
        return AdaptiveSizeComponent().also {
            adaptiveSizeComponent = it
            it.uriFragment = "_navigator_adaptive"
        }
    }

    fun getOrCreateDetachedNavItems(): MutableList<NavItem> {

        if (AdaptableSizeTreeBuilder::subTreeNavItems.isInitialized) {
            return subTreeNavItems
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    viewModel = Demo3PageTopBarViewModel.create(
                        "Orders/Current",
                        {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                ).apply {
                    uriFragment = "Current"
                }
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    viewModel = Demo3PageTopBarViewModel.create("Orders/Past", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                ).apply {
                    uriFragment = "Past"
                }
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    viewModel = Demo3PageTopBarViewModel.create("Orders/Claim", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                ).apply {
                    uriFragment = "Claim"
                }
            )
        )

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentDelegate = NavBarComponentDelegate1(),
            content = NavBarComponentDefaults.NavBarComponentView
        ).apply {
            uriFragment = "Orders"
        }

        navBarComponent.setNavItems(navbarNavItems, 0)

        val homeComponent = TopBarComponent(
            topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
            viewModel = HomeTopBarComponentViewModel.create("Home", {}),
            content = TopBarComponentDefaults.TopBarComponentView
        ).apply {
            uriFragment = "Home"
        }

        val settingsComponent = TopBarComponent(
            topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
            viewModel = SettingsTopBarViewModel.create("Settings", {}),
            content = TopBarComponentDefaults.TopBarComponentView
        ).apply {
            uriFragment = "Settings"
        }

        val navItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = homeComponent,
            ), NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = navBarComponent,
            ), NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = settingsComponent,
            )
        )

        return navItems.also { subTreeNavItems = it }
    }

}