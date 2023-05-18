package com.pablichj.incubator.uistate3.example.hotelBooking.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.Color
import com.pablichj.incubator.uistate3.example.hotelBooking.extra.SimpleComponent
import com.pablichj.incubator.uistate3.example.hotelBooking.hoteloffers.HotelOffersComponent
import com.pablichj.incubator.uistate3.example.hotelBooking.hotelsearch.HotelSearchComponent
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.stack.StackComponent

class HomeComponent : StackComponent(DefaultConfig) {

    private var hotelOffersComponent: HotelOffersComponent? = null

    private val hotelSearchComponent = HotelSearchComponent { hotelListing ->
        HotelOffersComponent(hotelListing).also {
            hotelOffersComponent = it
            it.setParent(this@HomeComponent)
            backStack.push(it)
        }
    }

    init {
        hotelSearchComponent.setParent(this)
    }

    override fun start() {
        super.start()
        println("HomeComponent::start()")
        if (activeComponent.value != null) {
            activeComponent.value?.start()
        } else {
            backStack.push(hotelSearchComponent)
        }
    }

    override fun getStackBarItemFromComponent(component: Component): StackBarItem {
        val selectedStackBarItem = if (component == hotelSearchComponent) {
            StackBarItem(
                label = "Hotel Search",
                icon = Icons.Default.Home,
            )
        } else {
            StackBarItem(
                label = "Hotel Offers",
                icon = Icons.Default.Search,
            )
        }
        return selectedStackBarItem
    }

    override fun onDestroyChildComponent(component: Component) {}
}