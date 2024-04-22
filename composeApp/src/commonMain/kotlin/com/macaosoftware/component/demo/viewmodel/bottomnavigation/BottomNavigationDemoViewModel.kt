package com.macaosoftware.component.demo.viewmodel.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.demo.viewmodel.topbar.Demo3PageTopBarViewModelFactory
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentViewModel
import com.macaosoftware.component.bottomnavigation.BottomNavigationStatePresenterDefault
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults

class BottomNavigationDemoViewModel(
    bottomNavigationComponent: BottomNavigationComponent<BottomNavigationDemoViewModel>,
    override val bottomNavigationStatePresenter: BottomNavigationStatePresenterDefault
) : BottomNavigationComponentViewModel(bottomNavigationComponent) {

    private var navItems: MutableList<NavItem>? = null

    override fun onAttach() {
        println("BottomNavigationDemoViewModel::onAttach()")
        setupNavItems()
    }

    override fun onStart() {
        println("BottomNavigationDemoViewModel::onStart()")
    }

    override fun onStop() {
        println("BottomNavigationDemoViewModel::onStop()")
    }

    override fun onDetach() {
        println("BottomNavigationDemoViewModel::onDetach()")
    }

    private fun setupNavItems() {

        // If already created lets reuse them
        navItems?.let { return }

        // Create and set the navItems for the demo the first time
        navItems = mutableListOf(
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

        navItems?.let {
            bottomNavigationComponent.setNavItems(it, selectedIndex = 0)
        }
    }

}
