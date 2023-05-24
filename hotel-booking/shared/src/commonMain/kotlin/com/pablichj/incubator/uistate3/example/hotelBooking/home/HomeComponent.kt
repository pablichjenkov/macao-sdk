package com.pablichj.incubator.uistate3.example.hotelBooking.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import com.pablichj.incubator.amadeus.endpoint.hotels.model.HotelListing
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.HotelOfferSearch
import com.pablichj.incubator.uistate3.example.hotelBooking.booking.OfferFullDetailComponent
import com.pablichj.incubator.uistate3.example.hotelBooking.booking.PaymentComponent
import com.pablichj.incubator.uistate3.example.hotelBooking.hoteloffers.HotelOffersComponent
import com.pablichj.incubator.uistate3.example.hotelBooking.hotelsearch.HotelSearchComponent
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.stack.StackComponent

class HomeComponent : StackComponent(DefaultConfig) {
    private var hotelSearchComponent: HotelSearchComponent? = null
    private var hotelOffersComponent: HotelOffersComponent? = null
    private var offerFullDetailComponent: OfferFullDetailComponent? = null
    private var paymentComponent: PaymentComponent? = null

    override fun start() {
        super.start()
        println("HomeComponent::start()")
        if (activeComponent.value != null) {
            activeComponent.value?.start()
        } else {
            launchHotelSearchComponent()
        }
    }

    override fun getStackBarItemFromComponent(component: Component): StackBarItem {
        return when (component) {
            is HotelSearchComponent -> {
                StackBarItem(
                    label = "Hotel Search",
                    icon = Icons.Default.Home,
                )
            }

            is HotelOffersComponent -> {
                StackBarItem(
                    label = "Hotel Offers",
                    icon = Icons.Default.Search,
                )
            }

            else -> {
                StackBarItem(
                    label = "Offer Booking",
                    icon = Icons.Default.Add,
                )
            }
        }
    }

    override fun onDestroyChildComponent(component: Component) {}

    private fun launchHotelSearchComponent() {
        HotelSearchComponent(
            onHotelSelected = { launchHotelOffersComponent(it) }
        ).also {
            hotelSearchComponent = it
            it.setParent(this@HomeComponent)
            backStack.push(it)
        }
    }

    private fun launchHotelOffersComponent(hotelListing: HotelListing) {
        HotelOffersComponent(
            hotelListing = hotelListing,
            onOfferSelected = {
                launchOfferFullDetailsComponent(it)
            }
        ).also {
            hotelOffersComponent = it
            it.setParent(this@HomeComponent)
            backStack.push(it)
        }
    }

    private fun launchOfferFullDetailsComponent(offer: HotelOfferSearch.Offer) {
        OfferFullDetailComponent(
            offerId = offer.id,
            onPurchaseClick = {
                launchOfferBookingComponent(it)
            }
        ).also {
            offerFullDetailComponent = it
            it.setParent(this@HomeComponent)
            backStack.push(it)
        }
    }

    private fun launchOfferBookingComponent(offer: HotelOfferSearch.Offer) {
        PaymentComponent(
            offer = offer,
            onPaymentResult = {

            }
        ).also {
            paymentComponent = it
            it.setParent(this@HomeComponent)
            backStack.push(it)
        }
    }
}