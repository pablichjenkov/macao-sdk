package com.pablichj.incubator.uistate3.example.hotelBooking.booking

sealed class OfferStage<out T> {
    class Success<T>(val value: T) : OfferStage<T>()
    object Error : OfferStage<Nothing>()
}