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
import com.macaosoftware.component.demo.viewmodel.factory.BottomNavigationDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.Demo3PageTopBarViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.DrawerComponentViewModelEmptyFactory
import com.macaosoftware.component.demo.viewmodel.factory.HomeTopBarViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.PanelComponentViewModelEmptyFactory
import com.macaosoftware.component.demo.viewmodel.factory.SettingsTopBarViewModelFactory
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentDefaults
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults

class AdaptiveSizeDemoViewModel(
    private val adaptiveSizeComponent: AdaptiveSizeComponent
) : AdaptiveSizeComponentViewModel() {

    private var subTreeNavItems: MutableList<NavItem>? = null

    override fun onCreate() {
        adaptiveSizeComponent.uriFragment = "_navigator_adaptive"
        val navItems = getOrCreateDetachedNavItems()
        adaptiveSizeComponent.setNavItems(navItems, 0)
        adaptiveSizeComponent.setCompactContainer(
            DrawerComponent(
                viewModelFactory = DrawerComponentViewModelEmptyFactory(),
                content = DrawerComponentDefaults.DrawerComponentView
            )
        )
        //adaptiveSizeComponent.setCompactContainer(PagerComponent())
        adaptiveSizeComponent.setMediumContainer(
            BottomNavigationComponent(
                viewModelFactory = BottomNavigationDemoViewModelFactory(
                    BottomNavigationComponentDefaults.createBottomNavigationStatePresenter()
                ),
                content = BottomNavigationComponentDefaults.BottomNavigationComponentView
            )
        )
        adaptiveSizeComponent.setExpandedContainer(
            PanelComponent(
                viewModelFactory = PanelComponentViewModelEmptyFactory(),
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
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Orders/Current",
                        onDone = {}
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
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Orders/Past",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                ).apply {
                    uriFragment = "Past"
                }
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Orders/Claim",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                ).apply {
                    uriFragment = "Claim"
                }
            )
        )

        val bottomNavigationComponent = BottomNavigationComponent(
            viewModelFactory = BottomNavigationDemoViewModelFactory(
                BottomNavigationComponentDefaults.createBottomNavigationStatePresenter()
            ),
            content = BottomNavigationComponentDefaults.BottomNavigationComponentView
        ).apply {
            uriFragment = "Orders"
        }

        bottomNavigationComponent.setNavItems(navbarNavItems, 0)

        val homeComponent = TopBarComponent(
            viewModelFactory = HomeTopBarViewModelFactory(
                topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                screenName = "Home",
                onDone = {}
            ),
            content = TopBarComponentDefaults.TopBarComponentView
        ).apply {
            uriFragment = "Home"
        }

        val settingsComponent = TopBarComponent(
            viewModelFactory = SettingsTopBarViewModelFactory(
                topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                screenName = "Settings",
                onDone = {}
            ),
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
                component = bottomNavigationComponent,
            ), NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = settingsComponent,
            )
        )

        return navItems.also { subTreeNavItems = it }
    }
}
