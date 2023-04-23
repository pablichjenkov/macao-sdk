package com.pablichj.incubator.amadeus.testdata

import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.*

object TestData {
    val HotelBookingBody = HotelBookingRequestBody(
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
}