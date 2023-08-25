package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarComponentDefaults
import com.pablichj.templato.component.core.pager.PagerComponent
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.core.topbar.TopBarComponentDefaults
import com.pablichj.templato.component.demo.componentDelegates.NavBarComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.TopBarComponentDelegate1

@OptIn(ExperimentalFoundationApi::class)
object PagerTreeBuilder {
    private lateinit var pagerComponent: PagerComponent

    fun build(): Component {

        if (this@PagerTreeBuilder::pagerComponent.isInitialized) {
            return pagerComponent
        }

        val navbarNavItems1 = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Settings", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Settings", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Orders/Claim", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )

        val navBarComponent1 = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentDelegate = NavBarComponentDelegate1(navbarNavItems1),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        val navbarNavItems2 = mutableListOf(
            NavItem(
                label = "Account",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Settings/Account", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Settings/Profile", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Settings/About Us", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )

        val navBarComponent2 = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentDelegate = NavBarComponentDelegate1(navbarNavItems2),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        val pagerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Settings", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = navBarComponent1.also { it.setNavItems(navbarNavItems1, 0) },
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = navBarComponent2.also { it.setNavItems(navbarNavItems2, 0) },
            )
        )

        return PagerComponent(
            content = PagerComponent.DefaultPagerComponentView
        ).also {
            pagerComponent = it
            it.setNavItems(
                pagerNavItems, 0
            )
        }
    }

}
