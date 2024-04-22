package com.macaosoftware.component.demo.viewmodel.panel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.demo.viewmodel.bottomnavigation.BottomNavigationDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.topbar.Demo3PageTopBarViewModelFactory
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentDefaults
import com.macaosoftware.component.demo.viewmodel.bottomnavigation.BottomNavigationDemoViewModel
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentViewModel
import com.macaosoftware.component.panel.PanelStatePresenterDefault
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults

class PanelDemoViewModel(
    panelComponent: PanelComponent<PanelDemoViewModel>,
    override val panelStatePresenter: PanelStatePresenterDefault
) : PanelComponentViewModel(panelComponent) {

    override fun onAttach() {
        val panelNavItems = createPanelNavItems()
        val selectedIndex = 0
        panelComponent.setNavItems(panelNavItems, selectedIndex)
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDetach() {
    }

    private fun createPanelNavItems(): List<NavItem> {
        return mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Home",
                        onDone = {}
                    ),
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
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Settings",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )
    }

    private fun buildNavBarComponent(): BottomNavigationComponent<BottomNavigationDemoViewModel> {

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Home",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Orders",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Settings",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )

        return BottomNavigationComponent(
            viewModelFactory = BottomNavigationDemoViewModelFactory(
                BottomNavigationComponentDefaults.createBottomNavigationStatePresenter()
            ),
            content = BottomNavigationComponentDefaults.BottomNavigationComponentView
        ).also { it.setNavItems(navbarNavItems, 0) }
    }

}
