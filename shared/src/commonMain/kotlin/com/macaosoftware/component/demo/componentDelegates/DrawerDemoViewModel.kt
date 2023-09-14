package com.macaosoftware.component.demo.componentDelegates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentViewModel
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.navbar.NavBarStatePresenterDefault
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault

class DrawerDemoViewModel : DrawerComponentViewModel<DrawerStatePresenterDefault>() {

    private lateinit var topBarComponent: TopBarComponent<TopBarStatePresenterDefault>
    private var currentComponent: Component? = null
    private var drawerNavItemsCache: MutableList<NavItem>? = null

    override fun create(drawerComponent: DrawerComponent<DrawerStatePresenterDefault>) {
        val drawerNavItems = createDrawerItems()
        val selectedIndex = 0
        drawerComponent.setNavItems(drawerNavItems, selectedIndex)
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }

    private fun createDrawerItems(): MutableList<NavItem> {
        drawerNavItemsCache?.let {
            return it
        }
        return mutableListOf(
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
                component = buildNavBarComponent(),
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
        ).also {
            drawerNavItemsCache = it
        }
    }

    private fun buildNavBarComponent(): NavBarComponent<NavBarStatePresenterDefault> {

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Active",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Active", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Past", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("New Order", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentViewModel = NavBarComponentDefaults.createComponentViewModel(),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        return navBarComponent.also { it.setNavItems(navbarNavItems, 0) }
    }

}
