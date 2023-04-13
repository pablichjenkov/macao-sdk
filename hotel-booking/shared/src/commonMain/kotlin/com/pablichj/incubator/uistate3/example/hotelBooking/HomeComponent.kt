package com.pablichj.incubator.uistate3.example.hotelBooking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.Color
import com.pablichj.incubator.uistate3.example.hotelBooking.extra.SimpleComponent
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.stack.StackBarItem
import com.pablichj.incubator.uistate3.node.stack.StackComponent

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

    init {
        homeComponent.setParent(this)
        searchComponent.setParent(this)
    }

    override fun start() {
        super.start()
        if (activeComponent.value != null) {
            println("HomeComponent::start()")
            activeComponent.value?.start()
        } else {
            backStack.push(homeComponent)
        }
    }

    override fun getStackBarItemFromComponent(component: Component): StackBarItem {
        val selectedStackBarItem = if (component == homeComponent) {
            StackBarItem(
                label = "Home",
                icon = Icons.Default.Home,
            )
        } else {
            StackBarItem(
                label = "Search",
                icon = Icons.Default.Search,
            )
        }
        return selectedStackBarItem
    }

    override fun onDestroyChildComponent(component: Component) {}
}