package com.pablichj.incubator.uistate3.example.hotelBooking.booking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.amadeus.common.CallResult
import com.pablichj.incubator.amadeus.endpoint.accesstoken.ResolveAccessTokenUseCaseSource
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.GetOfferRequest
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.GetOfferUseCase
import com.pablichj.incubator.amadeus.endpoint.offers.hotel.model.HotelOfferSearch
import com.pablichj.incubator.uistate3.example.hotelBooking.InMemoryAccessTokenDao
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.consumeBackPressEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OfferFullDetailComponent(
    private val offerId: String,
    private val onPurchaseClick: (HotelOfferSearch.Offer) -> Unit
) : Component() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val accessTokenDao = InMemoryAccessTokenDao()

    override fun start() {
        super.start()
        doRoomBooking()
    }

    private suspend fun getFullOfferDetails(offerId: String): HotelOfferSearch.Offer? {
        val accessToken = ResolveAccessTokenUseCaseSource(
            Dispatchers,
            accessTokenDao
        ).doWork()

        if (accessToken == null) {
            println("No saved token")
            return null
        } else {
            println("Using saved token: ${accessToken.accessToken}")
        }

        val callResult = GetOfferUseCase(
            Dispatchers
        ).doWork(
            GetOfferRequest(
                accessToken,
                offerId
            )
        )

        return when (callResult) {
            is CallResult.Error -> {
                println("Error in get offer by id: ${callResult.error}")
                null
            }

            is CallResult.Success -> {
                println("Offer in Hotel: ${callResult.responseBody.data.hotel.name}")
                return callResult.responseBody.data.offers.getOrNull(0)
            }
        }
    }

    private fun doRoomBooking() {
        coroutineScope.launch {
            val token = accessTokenDao.lastOrNull() ?: run {
                // getAccessToken()
                println("Token not found, start the flow again to get 1 token in the first step")
            }
            // todo: pass the access token in each request
            val offer = getFullOfferDetails(offerId)
            offerStage = if (offer == null) {
                OfferStage.Error
            } else {
                OfferStage.Success(offer)
            }
        }
    }

    private var offerStage by mutableStateOf<OfferStage<HotelOfferSearch.Offer>?>(null)

    @Composable
    override fun Content(modifier: Modifier) {
        println("OfferFullDetailComponent::Composing()")
        consumeBackPressEvent()
        when (val offerStageCopy = offerStage) {
            OfferStage.Error -> {
                Text("Error confirming the offer price")
            }

            is OfferStage.Success -> {
                OfferFullDetailView(
                    modifier = modifier,
                    offer = offerStageCopy.value,
                    onPurchaseClick = onPurchaseClick
                )
            }

            null -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp).align(Alignment.Center)
                    )
                }
            }
        }
    }

}
