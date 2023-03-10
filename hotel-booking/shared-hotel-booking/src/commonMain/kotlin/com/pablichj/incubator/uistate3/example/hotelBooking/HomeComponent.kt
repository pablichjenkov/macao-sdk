package com.pablichj.incubator.uistate3.example.hotelBooking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.Color
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.topbar.StackComponent
import example.nodes.SimpleComponent

class HomeComponent : StackComponent() {

    val homeComponent = SimpleComponent(
        "Home Page",
        Color.Cyan
    ) {
        backStack.push(searchComponent)
    }

    val searchComponent = SimpleComponent(
        "Search Page",
        Color.Yellow
    ) {}


    override fun start() {
        super.start()
        if (activeComponent.value != null) {
            println("HomeComponent::start()")
            activeComponent.value?.start()
        } else {
            backStack.push(homeComponent)
        }
    }

    protected override fun updateSelectedComponent(newTop: Component) {
        //val selectedNavItem = getNavItemFromNode(newTop)
        val selectedNavItem = if (newTop == homeComponent) {
            NavItem(
                label = "Home",
                icon = Icons.Default.Home,
                homeComponent
            )
        } else {
            NavItem(
                label = "Search",
                icon = Icons.Default.Search,
                searchComponent
            )
        }

        if (backStack.size() > 1) {
            setTitleSectionForBackClick(selectedNavItem)
        } else {
            setTitleSectionForHomeClick(selectedNavItem)
        }
    }

    override fun onDestroyChildComponent(component: Component) {

    }

}