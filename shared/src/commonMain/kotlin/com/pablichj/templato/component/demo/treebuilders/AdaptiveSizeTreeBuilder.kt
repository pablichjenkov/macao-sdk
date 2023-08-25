package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.adaptive.AdaptiveSizeComponent
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarComponentDefaults
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.core.topbar.TopBarComponentDefaults
import com.pablichj.templato.component.demo.componentDelegates.NavBarComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.TopBarComponentDelegate1

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
                    componentDelegate = TopBarComponentDelegate1.create(
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
                    componentDelegate = TopBarComponentDelegate1.create("Orders/Past", {}),
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
                    componentDelegate = TopBarComponentDelegate1.create("Orders/Claim", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                ).apply {
                    uriFragment = "Claim"
                }
            )
        )

        val NavBarComponentDelegate1 = NavBarComponentDelegate1(
            navbarNavItems
        )

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentDelegate = NavBarComponentDelegate1,
            content = NavBarComponentDefaults.NavBarComponentView
        ).apply {
            uriFragment = "Orders"
        }

        navBarComponent.setNavItems(navbarNavItems, 0)

        val homeComponent = TopBarComponent(
            topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
            componentDelegate = TopBarComponentDelegate1.create("Home", {}),
            content = TopBarComponentDefaults.TopBarComponentView
        ).apply {
            uriFragment = "Home"
        }

        val settingsComponent = TopBarComponent(
            topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
            componentDelegate = TopBarComponentDelegate1.create("Settings", {}),
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