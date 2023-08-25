package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.drawer.DrawerComponentDefaults
import com.pablichj.templato.component.core.drawer.DrawerStatePresenterDefault
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarComponentDefaults
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.split.SplitComponent
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.core.topbar.TopBarComponentDefaults
import com.pablichj.templato.component.demo.AppCoordinatorComponent
import com.pablichj.templato.component.demo.componentDelegates.DrawerComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.NavBarComponentDelegate1
import com.pablichj.templato.component.demo.componentDelegates.TopBarComponentDelegate1
import com.pablichj.templato.component.platform.CoroutineDispatchers
import com.pablichj.templato.component.platform.DiContainer

object FullAppWithIntroTreeBuilder {
    private val diContainer = DiContainer(CoroutineDispatchers.Defaults)
    private lateinit var appCoordinatorComponent: Component

    fun build(): Component {

        if (FullAppWithIntroTreeBuilder::appCoordinatorComponent.isInitialized) {
            return appCoordinatorComponent
        }

        return AppCoordinatorComponent().also {
            it.homeComponent = buildDrawerStateTree(it)
            appCoordinatorComponent = it
        }
    }

    private fun buildDrawerStateTree(parentComponent: Component): Component {

        val splitComponent = SplitComponent(SplitComponent.DefaultConfig).apply {
            setTopComponent(buildNestedDrawer())
            setBottomComponent(
                TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Orders / Current", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Orders/Current", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Nested Node",
                icon = Icons.Filled.Email,
                component = splitComponent
            )
        )

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentDelegate = NavBarComponentDelegate1(navbarNavItems),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Home", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = navBarComponent.also {
                    it.setNavItems(navbarNavItems, 0)
                },
            )
        )

        val drawerComponent = DrawerComponent(
            drawerStatePresenter = DrawerComponentDefaults.createDrawerStatePresenter(),
            componentDelegate = DrawerComponentDelegate1(drawerNavItems),
            content = DrawerComponentDefaults.DrawerComponentView
        )

        return drawerComponent.apply {
            setParent(parentComponent)
            setNavItems(drawerNavItems, 0)
        }
    }

    private fun buildNestedDrawer(): DrawerComponent<DrawerStatePresenterDefault> {

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Orders/Current", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Orders/Past", {}),
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

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentDelegate = NavBarComponentDelegate1(navbarNavItems),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home Nested",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentDelegate = TopBarComponentDelegate1.create("Home Nested", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Orders Nested",
                icon = Icons.Filled.Edit,
                component = navBarComponent.also { it.setNavItems(navbarNavItems, 0) },
            )
        )

        val drawerComponent = DrawerComponent(
            drawerStatePresenter = DrawerComponentDefaults.createDrawerStatePresenter(),
            componentDelegate = DrawerComponentDelegate1(drawerNavItems),
            content = DrawerComponentDefaults.DrawerComponentView
        )

        return drawerComponent.also { it.setNavItems(drawerNavItems, 0) }
    }

}
