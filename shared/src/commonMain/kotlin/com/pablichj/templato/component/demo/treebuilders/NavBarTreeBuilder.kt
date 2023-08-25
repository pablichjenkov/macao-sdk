package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarComponentDefaults
import com.pablichj.templato.component.core.navbar.NavBarStatePresenterDefault
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.demo.componentDelegates.NavBarComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.TopBarComponentDelegate1

object NavBarTreeBuilder {

    private lateinit var navBarComponent: NavBarComponent<NavBarStatePresenterDefault>

    fun build(): NavBarComponent<NavBarStatePresenterDefault> {

        if (NavBarTreeBuilder::navBarComponent.isInitialized) {
            return navBarComponent
        }

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
            // pushStrategy = FixSizedPushStrategy(1), // Uncomment to test other push strategies
            componentDelegate = NavBarComponentDelegate1(navbarNavItems),
            content = NavBarComponentDefaults.NavBarComponentView
        ).also {
            it.setNavItems(navbarNavItems, 0)
            navBarComponent = it
        }
    }

}
