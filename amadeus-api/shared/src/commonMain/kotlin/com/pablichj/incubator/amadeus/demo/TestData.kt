package com.pablichj.incubator.amadeus.demo

import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.*
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.FlightOffersRequestBody
import com.pablichj.incubator.amadeus.endpoint.offers.flight.model.*

object TestData {
    val hotelBookingRequestBody = HotelBookingRequestBody(
        data = HotelBookingRequestData(
            offerId = "NRPQNQBOJM",
            guests = listOf(
                Guest(
                    name = Name(
                        title = "MR",
                        firstName = "BOB",
                        lastName = "SMITH"
                    ),
                    contact = Contact(
                        phone = "+33679278416",
                        email = "bob.smith@email.com"
                    )
                )
            ),
            payments = listOf(
                Payment(
                    method = "creditCard",
                    card = Card(
                        vendorCode = "VI",
                        cardNumber = "0000000000000000",
                        expiryDate = "2026-01"
                    )
                )
            )
        )
    )
    val flightOffersRequestBody = FlightOffersRequestBody(
        currencyCode = "USD",
        originDestinations = listOf(
            OriginDestination(
                id = "1",
                originLocationCode = "MIA",
                destinationLocationCode = "CUN",
                departureDateTimeRange = DepartureDateTimeRange(
                    date = "2023-11-01",
                    time = "10:00:00",
                )
            )
        ),
        travelers = listOf(
            Traveler(
                id = "1",
                travelerType = "ADULT"
            )
        ),
        sources = listOf("GDS"),
        searchCriteria = SearchCriteria(
            maxFlightOffers = 3,
            flightFilters = FlightFilters(
                cabinRestrictions = listOf(
                    CabinRestriction(
                        cabin = "BUSINESS",
                        coverage = "MOST_SEGMENTS",
                        originDestinationIds = listOf("1")
                    )
                )
            )
        )
    )
}