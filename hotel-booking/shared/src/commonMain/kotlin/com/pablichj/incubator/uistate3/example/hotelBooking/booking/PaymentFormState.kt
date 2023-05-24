package com.pablichj.incubator.uistate3.example.hotelBooking.booking

import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.Card
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.Contact
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.Guest
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.HotelBookingRequestBody
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.HotelBookingRequestData
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.Name
import com.pablichj.incubator.amadeus.endpoint.booking.hotel.model.Payment

class PaymentFormState {
    var firstName: String? = null
    var lastName: String? = null
    var phone: String? = null
    var email: String? = null
    val paymentMethod = "creditCard"
    var cardBrand: String? = null
    var cardNumber: String? = null
    var cardExpDate: String? = null

    fun toRoomOfferBookingRequestBody(offerId: String): HotelBookingRequestBody {
        return HotelBookingRequestBody(
            data = HotelBookingRequestData(
                offerId = offerId,
                guests = listOf(
                    Guest(
                        name = Name(
                            title = "MR",
                            firstName = firstName.orEmpty(),
                            lastName = lastName.orEmpty()
                        ),
                        contact = Contact(
                            phone = phone.orEmpty(),
                            email = email.orEmpty()
                        )
                    )
                ),
                payments = listOf(
                    Payment(
                        method = paymentMethod,
                        card = Card(
                            vendorCode = cardBrand.orEmpty(),
                            cardNumber = cardNumber.orEmpty(),
                            expiryDate = cardExpDate.orEmpty()
                        )
                    )
                )
            )
        )
    }
}
