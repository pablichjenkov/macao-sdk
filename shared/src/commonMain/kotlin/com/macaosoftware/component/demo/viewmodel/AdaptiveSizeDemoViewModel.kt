package com.macaosoftware.component.demo.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.adaptive.AdaptiveSizeComponentViewModel
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults

class AdaptiveSizeDemoViewModel : AdaptiveSizeComponentViewModel() {

    private lateinit var adaptiveSizeComponent: AdaptiveSizeComponent
    private var subTreeNavItems: MutableList<NavItem>? = null

    override fun onCreate(adaptiveSizeComponent: AdaptiveSizeComponent) {
        this.adaptiveSizeComponent = adaptiveSizeComponent
        adaptiveSizeComponent.uriFragment = "_navigator_adaptive"
        val navItems = getOrCreateDetachedNavItems()
        adaptiveSizeComponent.setNavItems(navItems, 0)
        adaptiveSizeComponent.setCompactContainer(
            DrawerComponent(
                componentViewModel = DrawerComponentDefaults.createComponentViewModel(),
                content = DrawerComponentDefaults.DrawerComponentView
            )
        )
        //adaptiveSizeComponent.setCompactContainer(PagerComponent())
        adaptiveSizeComponent.setMediumContainer(
            NavBarComponent(
                navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
                componentViewModel = NavBarComponentDefaults.createComponentViewModel(),
                content = NavBarComponentDefaults.NavBarComponentView
            )
        )
        adaptiveSizeComponent.setExpandedContainer(
            PanelComponent(
                panelStatePresenter = PanelComponentDefaults.createPanelStatePresenter(),
                componentViewModel = PanelComponentDefaults.createComponentViewModel(),
                content = PanelComponentDefaults.PanelComponentView
            )
        )

    }

    override fun onStart() {
        println("AdaptiveSizeDemoViewModel::onStart()")
    }

    override fun onStop() {
        println("AdaptiveSizeDemoViewModel::onStop()")
    }

    override fun onDestroy() {
        println("AdaptiveSizeDemoViewModel::onDestroy()")
    }

    fun getOrCreateDetachedNavItems(): MutableList<NavItem> {

        subTreeNavItems?.let {
            return it
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create(
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
                    componentViewModel = Demo3PageTopBarViewModel.create("Orders/Past", {}),
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
                    componentViewModel = Demo3PageTopBarViewModel.create("Orders/Claim", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                ).apply {
                    uriFragment = "Claim"
                }
            )
        )

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentViewModel = NavBarComponentDefaults.createComponentViewModel(),
            content = NavBarComponentDefaults.NavBarComponentView
        ).apply {
            uriFragment = "Orders"
        }

        navBarComponent.setNavItems(navbarNavItems, 0)

        val homeComponent = TopBarComponent(
            topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
            componentViewModel = HomeTopBarViewModel.create("Home", {}),
            content = TopBarComponentDefaults.TopBarComponentView
        ).apply {
            uriFragment = "Home"
        }

        val settingsComponent = TopBarComponent(
            topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
            componentViewModel = SettingsTopBarViewModel.create("Settings", {}),
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