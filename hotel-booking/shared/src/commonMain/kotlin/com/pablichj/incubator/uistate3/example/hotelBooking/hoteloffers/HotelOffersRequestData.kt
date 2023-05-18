package com.pablichj.incubator.uistate3.example.hotelBooking.hoteloffers

data class HotelOffersRequestData(
    val hotelId: String,
    val checkingDate: String,
    val numberOfAdults: String,
    val roomQuantity: String
)
